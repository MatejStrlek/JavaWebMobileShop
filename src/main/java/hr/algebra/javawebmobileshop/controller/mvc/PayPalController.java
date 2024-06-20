package hr.algebra.javawebmobileshop.controller.mvc;

import com.paypal.http.HttpResponse;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import hr.algebra.javawebmobileshop.model.CartItem;
import hr.algebra.javawebmobileshop.model.Purchase;
import hr.algebra.javawebmobileshop.model.PurchaseItem;
import hr.algebra.javawebmobileshop.model.User;
import hr.algebra.javawebmobileshop.paypal.PayPalService;
import hr.algebra.javawebmobileshop.service.CartService;
import hr.algebra.javawebmobileshop.service.MyUserDetailsService;
import hr.algebra.javawebmobileshop.service.PurchaseService;
import hr.algebra.javawebmobileshop.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/public/shop/paypal")
public class PayPalController {
    private final CartService cartService;
    private final PayPalService payPalService;
    private final MyUserDetailsService userDetailsService;
    private final PurchaseService purchaseService;

    private static final Logger logger = LoggerFactory.getLogger(PayPalController.class);

    @Autowired
    public PayPalController(CartService cartService, PayPalService payPalService, MyUserDetailsService userDetailsService, PurchaseService purchaseService) {
        this.cartService = cartService;
        this.payPalService = payPalService;
        this.userDetailsService = userDetailsService;
        this.purchaseService = purchaseService;
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buy(@RequestParam("paymentMethod") String paymentMethod) {
        List<CartItem> cartItems = cartService.getCartItems();
        Double total = cartService.calculateTotal(cartItems);

        try {
            HttpResponse<Order> orderResponse = payPalService.createOrder(
                    total,
                    "USD",
                    "http://localhost:8080/public/shop/paypal/cancel",
                    "http://localhost:8080/public/shop/paypal/success");

            logger.info("PayPal Order Created: {}", orderResponse.result());

            for (LinkDescription link : orderResponse.result().links()) {
                if ("approve".equals(link.rel())) {
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .header(HttpHeaders.LOCATION, link.href())
                            .build();
                }
            }
        } catch (IOException e) {
            logger.error("Error creating PayPal order", e);
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/public/shop")
                .build();
    }

    @GetMapping("/success")
    public ResponseEntity<String> successPay(@RequestParam("token") String orderId, Model model) {
        try {
            HttpResponse<Order> captureResponse = payPalService.captureOrder(orderId);
            logger.info("PayPal Order Captured: {}", captureResponse.result());

            if ("COMPLETED".equals(captureResponse.result().status())) {
                List<CartItem> cartItems = cartService.getCartItems();
                purchaseService.savePurchase(purchaseItems(cartItems));
                cartService.clearCart();
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.LOCATION, "/public/shop/response");
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            }
        } catch (IOException e) {
            logger.error("Error capturing PayPal order", e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/shop/shop.html");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    private Purchase purchaseItems(List<CartItem> cartItems) {
        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(DateUtils.format(LocalDateTime.now()));
        purchase.setPaymentMethod("PayPal");
        User currentUser = userDetailsService.getCurrentUser();
        purchase.setUser(currentUser);
        List<PurchaseItem> purchaseItems = cartItems.stream()
                .map(item -> new PurchaseItem(
                        null,
                        item.getMobile().getName(),
                        item.getMobile().getBrand(),
                        item.getMobile().getPrice(),
                        item.getQuantity(),
                        purchase))
                .toList();
        purchase.setPurchaseItems(purchaseItems);
        return purchase;
    }

    @GetMapping("/cancel")
    public String cancelPay() {
        return "redirect:/public/shop/cart";
    }
}
