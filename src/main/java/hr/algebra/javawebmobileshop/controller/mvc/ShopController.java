package hr.algebra.javawebmobileshop.controller.mvc;

import hr.algebra.javawebmobileshop.model.CartItem;
import hr.algebra.javawebmobileshop.model.Mobile;
import hr.algebra.javawebmobileshop.model.MobileCategory;
import hr.algebra.javawebmobileshop.service.MobileCategoryService;
import hr.algebra.javawebmobileshop.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import hr.algebra.javawebmobileshop.service.CartService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Controller
@RequestMapping("/public/shop")
public class ShopController {
    private final CartService cartService;
    private final MobileCategoryService categoryService;
    private final MobileService mobileService;

    @Autowired
    public ShopController(CartService cartService, MobileCategoryService categoryService, MobileService mobileService) {
        this.cartService = cartService;
        this.categoryService = categoryService;
        this.mobileService = mobileService;
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
}

