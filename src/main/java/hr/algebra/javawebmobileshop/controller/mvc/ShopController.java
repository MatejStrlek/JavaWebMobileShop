package hr.algebra.javawebmobileshop.controller.mvc;

import hr.algebra.javawebmobileshop.model.*;
import hr.algebra.javawebmobileshop.service.MobileCategoryService;
import hr.algebra.javawebmobileshop.service.MobileService;
import hr.algebra.javawebmobileshop.service.PurchaseService;
import hr.algebra.javawebmobileshop.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import hr.algebra.javawebmobileshop.service.CartService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/public/shop")
public class ShopController {
    private final CartService cartService;
    private final MobileCategoryService categoryService;
    private final MobileService mobileService;
    private final PurchaseService purchaseService;

    @Autowired
    public ShopController(CartService cartService, MobileCategoryService categoryService,
                          MobileService mobileService, PurchaseService purchaseService) {
        this.cartService = cartService;
        this.categoryService = categoryService;
        this.mobileService = mobileService;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public String viewShop(Model model) {
        List<MobileCategory> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "shop/shop";
    }

    @GetMapping("/category/{id}")
    public String viewMobilesByCategory(@PathVariable Long id, Model model) {
        MobileCategory category = categoryService
                .getCategoryById(id).orElseThrow(() ->
                        new IllegalArgumentException("Invalid category Id:" + id));
        List<Mobile> mobiles = mobileService.getMobilesByCategoryId(id);
        model.addAttribute("category", category);
        model.addAttribute("mobiles", mobiles);
        return "shop/category";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        double grandTotal = cartItems.stream()
                .mapToDouble(item -> item.getMobile().getPrice() * item.getQuantity())
                .sum();
        BigDecimal roundedGrandTotal = new BigDecimal(grandTotal).setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("grandTotal", roundedGrandTotal);
        return "cart/view";
    }

    @PostMapping("/cart/add")
    public String addItemToCart(@RequestParam Long mobileId, @RequestParam int quantity) {
        cartService.addItemToCart(mobileId, quantity);
        return "redirect:/public/shop/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeItemFromCart(@PathVariable Long id) {
        cartService.removeItemFromCart(id);
        return "redirect:/public/shop/cart";
    }

    @PostMapping("/cart/update/{id}")
    public String updateItemQuantity(@PathVariable Long id, @RequestParam int quantity) {
        cartService.updateItemQuantity(id, quantity);
        return "redirect:/public/shop/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/public/shop/cart";
    }

    @PostMapping("/cart/buy")
    public String buyCart(@RequestParam("paymentMethod") String paymentMethod, Model model) {
        List<CartItem> cartItems = cartService.getCartItems();

        if (cartItems.isEmpty()) {
            model.addAttribute("errorMessage", "Your cart is empty. Please add items to your cart before proceeding to checkout.");
            return "cart/view";
        }

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(DateUtils.format(LocalDateTime.now()));
        purchase.setPaymentMethod(paymentMethod);

        List<PurchaseItem> purchaseItems = cartItems.stream()
                .map(item -> new PurchaseItem(
                        null,
                        item.getMobile().getName(),
                        item.getMobile().getBrand(),
                        item.getMobile().getPrice(),
                        item.getQuantity(),
                        purchase))
                .collect(Collectors.toList());

        purchase.setPurchaseItems(purchaseItems);
        purchaseService.savePurchase(purchase);
        cartService.clearCart();

        return "redirect:/public/shop/purchase-history";
    }

    @GetMapping("/purchase-history")
    public String viewPurchaseHistory(Model model) {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        model.addAttribute("purchases", purchases);
        return "shop/purchase-history";
    }
}

