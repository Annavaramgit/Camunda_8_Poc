package com.aus.corsafe.service;

import com.aus.corsafe.config.ApplicationConfig;
import com.aus.corsafe.entity.Cart;
import com.aus.corsafe.entity.CartItem;
import com.aus.corsafe.entity.Products;
import com.aus.corsafe.entity.UserRegister;
import com.aus.corsafe.exceptions.ProductNotFoundException;
import com.aus.corsafe.exceptions.UserNotFoundExceptionCls;
import com.aus.corsafe.repository.CartRepository;
import com.aus.corsafe.repository.ProductRepository;
import com.aus.corsafe.repository.UserRegisterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRegisterRepo userRegisterRepo;

    @Autowired
    private ProductRepository productRepository;

    /*adding product to the cart */
    public Cart addProductToUserCart(Integer userId, Integer productId, Integer quantity) throws ProductNotFoundException {

        log.info("addProductToUserCart entered, userId is: {}", userId);

        if(userId==null){
            throw new UserNotFoundExceptionCls("usernot found"+userId);
        }


        userRegisterRepo.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundExceptionCls("User not found with ID: " + userId));

        log.info("user id is: {}", userId);

        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(userId));
        log.info("cart details {}", cart);


        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            log.info("product already there: {}", existingItem.isPresent());
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            log.info("else block means cart not there for user... ");
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPrice((double) product.getPrice());
            newItem.setUserId(userId);

            cart.getItems().add(newItem);
        }

        cart.updateTotalPrice();
        return cartRepository.save(cart);


    }



    public Cart removeItemFromUserCart(Integer userId, Integer productId, Integer quantityToRemove) throws ProductNotFoundException {
        log.info("removeItemFromUserCart entered, userId: {}, productId: {}, quantityToRemove: {}", userId, productId, quantityToRemove);

        // Validate user existence
        userRegisterRepo.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundExceptionCls("User not found with ID: " + userId));

        // Retrieve the user's cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ProductNotFoundException("Cart not found for user ID: " + userId));

        // Find the item to be updated or removed
        Optional<CartItem> itemToModify = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

        if (itemToModify.isPresent()) {
            CartItem cartItem = itemToModify.get();
            log.info("Current quantity: {}, quantity to remove: {}", cartItem.getQuantity(), quantityToRemove);

            // Check if quantity to remove is less than or equal to the current quantity
            if (cartItem.getQuantity() > quantityToRemove) {
                cartItem.setQuantity(cartItem.getQuantity() - quantityToRemove);
            } else {
                // Remove item from cart if quantity becomes zero or less
                cart.getItems().remove(cartItem);
                log.info("Item quantity reduced to zero, removing item from cart");
            }

            // Update the cart's total price
            cart.updateTotalPrice();
            return cartRepository.save(cart);
        } else {
            throw new ProductNotFoundException("Product not found in the cart");
        }
    }



    public Cart addQuantityToUserCart(Integer userId, Integer productId, Integer quantityToAdd) throws ProductNotFoundException {
        log.info("addQuantityToUserCart called with userId: {}, productId: {}, quantityToAdd: {}", userId, productId, quantityToAdd);

        // Validate user existence
        userRegisterRepo.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundExceptionCls("User not found with ID: " + userId));

        // Retrieve or create a new cart for the user
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(userId));

        // Find the cart item
        Optional<CartItem> itemToModify = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

        if (itemToModify.isPresent()) {
            CartItem cartItem = itemToModify.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantityToAdd); // Add quantity
            cartItem.setPrice(cartItem.getProduct().getPrice()); // Ensure price is updated
        } else {
            // If product is not in cart, add it as a new item
            Products product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantityToAdd);
            newItem.setPrice(product.getPrice());

            cart.getItems().add(newItem);
        }

        cart.updateTotalPrice(); // Update total price in cart
        return cartRepository.save(cart);
    }

    public Cart removeQuantityFromUserCart(Integer userId, Integer productId, Integer quantityToRemove) throws ProductNotFoundException {
        log.info("removeQuantityFromUserCart called with userId: {}, productId: {}, quantityToRemove: {}", userId, productId, quantityToRemove);

        // Validate user existence
        userRegisterRepo.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundExceptionCls("User not found with ID: " + userId));

        // Retrieve the cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ProductNotFoundException("Cart not found for user ID: " + userId));

        // Find the cart item
        Optional<CartItem> itemToModify = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

        if (itemToModify.isPresent()) {
            CartItem cartItem = itemToModify.get();
            if (cartItem.getQuantity() > quantityToRemove) {
                cartItem.setQuantity(cartItem.getQuantity() - quantityToRemove); // Decrease quantity
            } else {
                // Remove item if quantity goes to zero or below
                cart.getItems().remove(cartItem);
            }
        } else {
            throw new ProductNotFoundException("Product not found in the cart");
        }

        cart.updateTotalPrice(); // Update total price in cart
        return cartRepository.save(cart);
    }


    public List<CartItem> getCartItemsByCartId(int userId) throws ProductNotFoundException {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ProductNotFoundException("Cart not found with ID: " + userId));
        return cart.getItems();
    }
    //get total price from cart table
    public Map<String, Object> getTotalPrice(Integer userId) {

        Optional<Cart> byUserId = cartRepository.findByUserId(userId);

        if (!byUserId.isPresent()) {
            throw new UserNotFoundExceptionCls("Cart not found for user ID: " + userId);
        }
        Cart cart = byUserId.get();

        Map<String, Object> cartDetails = new HashMap<>();
        cartDetails.put("totalPrice", cart.getTotalPrice());
        cartDetails.put("cartId", cart.getCartId());

        return cartDetails;


    }
}