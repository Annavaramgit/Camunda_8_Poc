package com.aus.corsafe.controller;

import com.aus.corsafe.config.ApplicationConfig;
import com.aus.corsafe.dto.CartAddDto;
import com.aus.corsafe.entity.*;
import com.aus.corsafe.exceptions.ProductNotFoundException;
import com.aus.corsafe.repository.CartRepository;
import com.aus.corsafe.repository.ProductRepository;
import com.aus.corsafe.repository.UserRegisterRepo;
//import com.aus.corsafe.response.CommonResponse;
import com.aus.corsafe.response.CommonResponse;
import com.aus.corsafe.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/cart")
//@CrossOrigin(origins = "http://localhost:3000/**", allowCredentials = "true")
public class CartController {


    @Autowired
    private UserRegisterRepo userRegisterRepo;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;


    @PostMapping("/cartadd")
    public ResponseEntity<ResponseModel<Object>> addProductToCart(@RequestBody CartAddDto cartAddDto) {
        try {
            log.info("Adding product to cart - userId: {}, productId: {}, quantity: {}",
                    cartAddDto.getUserId(), cartAddDto.getProductId(), cartAddDto.getQuantity());

            Cart updatedCart = cartService.addProductToUserCart(cartAddDto.getUserId(), cartAddDto.getProductId(), cartAddDto.getQuantity());
            return new CommonResponse<>().prepareSuccessResponseObject(updatedCart, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            log.info("ProductNotFoundException: {}", e.getMessage());
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error occurred: ", e);
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/removeItem")
    public ResponseEntity<ResponseModel<Object>> removeItemFromCart(@RequestBody CartAddDto cartAddDto) {
        try {
            log.info("Removing product from cart - userId: {}, productId: {}, quantity: {}",
                    cartAddDto.getUserId(), cartAddDto.getProductId(), cartAddDto.getQuantity());

            Cart updatedCart = cartService.removeItemFromUserCart(cartAddDto.getUserId(), cartAddDto.getProductId(), cartAddDto.getQuantity());
            return new CommonResponse<>().prepareSuccessResponseObject(updatedCart, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            log.info("ProductNotFoundException: {}", e.getMessage());
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error occurred: ", e);
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addQuantity")
    public ResponseEntity<ResponseModel<Object>> addQuantityToItem(@RequestBody CartAddDto cartAddDto) {
        try {
            log.info("Adding quantity for ProductId: {} in userId: {}", cartAddDto.getProductId(), cartAddDto.getUserId());

            // Call the service method with a quantity of 1
            Cart updatedCart = cartService.addQuantityToUserCart(cartAddDto.getUserId(), cartAddDto.getProductId(), 1);
            return new CommonResponse<>().prepareSuccessResponseObject(updatedCart, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            log.info("ProductNotFoundException: {}", e.getMessage());
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error occurred: ", e);
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/removeQuantity")
    public ResponseEntity<ResponseModel<Object>> removeQuantityFromItem(@RequestBody CartAddDto cartAddDto) {
        try {
            log.info("Removing quantity for ProductId: {} in userId: {}", cartAddDto.getProductId(), cartAddDto.getUserId());

            // Call the service method with a quantity of 1
            Cart updatedCart = cartService.removeQuantityFromUserCart(cartAddDto.getUserId(), cartAddDto.getProductId(), 1);
            return new CommonResponse<>().prepareSuccessResponseObject(updatedCart, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            log.info("ProductNotFoundException: {}", e.getMessage());
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error occurred: ", e);
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/cartitems/{userId}")
    public ResponseEntity<ResponseModel<Object>> getCartItems(@PathVariable int userId) {
        try {
            List<CartItem> cartItems = cartService.getCartItemsByCartId(userId);
            if (cartItems.isEmpty()) {
                return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.NO_ITEMS_FOUND, HttpStatus.NO_CONTENT);
            }
            return new CommonResponse<>().prepareSuccessResponseObject(cartItems, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving cart items for cartId {}: {}", userId, e.getMessage());
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTotalPrice/{userId}")
    public ResponseEntity<ResponseModel<Object>> getTotalPriceFromCart(@PathVariable Integer userId) {
        try {
            Map<String, Object> cartDetails = cartService.getTotalPrice(userId);
            return new CommonResponse<>().prepareSuccessResponseObject(cartDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.USER_OR_CART_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

    }
}