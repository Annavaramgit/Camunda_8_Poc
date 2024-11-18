package com.aus.corsafe.controller;

import com.aus.corsafe.config.ApplicationConfig;
import com.aus.corsafe.dto.OrderDto;
import com.aus.corsafe.entity.Cart;
import com.aus.corsafe.entity.Order;
import com.aus.corsafe.entity.ResponseModel;
import com.aus.corsafe.entity.UserRegister;
import com.aus.corsafe.repository.CartRepository;
import com.aus.corsafe.repository.UserRegisterRepo;
import com.aus.corsafe.response.CommonResponse;
import com.aus.corsafe.service.OrderService;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000/**" , allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRegisterRepo userRepo;

    @Autowired
    private CartRepository cartRepo;


    @PostMapping("/placeorder")
    public ResponseEntity<ResponseModel<Object>> placeOrder(@RequestBody OrderDto dto) throws RazorpayException {
        log.info("placeOrder controller entered");
        log.info("userid: {}, cartid: {}", dto.getUserId(), dto.getCartId());

        Optional<UserRegister> userOptional = userRepo.findByUserId(dto.getUserId());
        Optional<Cart> cartOptional = cartRepo.findByCartId(dto.getCartId());
        log.info("user is present: {}, cartid present: {}", userOptional.isPresent(), cartOptional.isPresent());

        if (userOptional.isPresent() && cartOptional.isPresent()) {
            UserRegister user = userOptional.get();
            Cart cart = cartOptional.get();
            log.info("placeOrder controller if block");

            Order order = orderService.placeOrder(cart, user, dto.getAdress(), dto.getCity(), dto.getPostalcode(), dto.getArea());
            return new CommonResponse<>().prepareSuccessResponseObject(order, HttpStatus.OK);
        } else {
            log.info("placeOrder controller else block");
            // If either the user or cart is not found, return an error response
            return new CommonResponse<>().prepareErrorResponseObject(ApplicationConfig.USER_OR_CART_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }


}