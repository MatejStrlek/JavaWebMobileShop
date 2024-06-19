package hr.algebra.javawebmobileshop.service;

import hr.algebra.javawebmobileshop.model.CartItem;
import hr.algebra.javawebmobileshop.model.Mobile;
import hr.algebra.javawebmobileshop.repo.CartItemRepository;
import hr.algebra.javawebmobileshop.repo.MobileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final MobileRepository mobileRepository;


    @Autowired
    public CartService(CartItemRepository cartItemRepository, MobileRepository mobileRepository) {
        this.cartItemRepository = cartItemRepository;
        this.mobileRepository = mobileRepository;
    }

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public void addItemToCart(Long mobileId, int quantity) {
        Mobile mobile = mobileRepository
                .findById(mobileId).orElseThrow(() ->
                        new IllegalArgumentException("Invalid mobile Id:" + mobileId));
        CartItem cartItem = new CartItem(
                null,
                mobile,
                quantity
        );
        cartItemRepository.save(cartItem);
    }

    public void removeItemFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void updateItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository
                .findById(cartItemId).orElseThrow(() ->
                        new IllegalArgumentException("Invalid cart item Id:" + cartItemId));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    public void clearCart() {
        cartItemRepository.deleteAll();
    }
}