package com.aus.corsafe.service;

import com.aus.corsafe.entity.Cart;
import com.aus.corsafe.entity.CartItem;
import com.aus.corsafe.entity.Order;
import com.aus.corsafe.entity.UserRegister;
import com.aus.corsafe.repository.CartItemRepo;
import com.aus.corsafe.repository.OrderRepo;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderService {


    @Value("${razorpay.key.id}")
    private String razorPayKey;

    @Value("${razorpay.secret.key}")
    private String razorPaySecret;

    @Autowired
    private CartItemRepo cartItemRepo;

    private RazorpayClient client;

//    @Autowired
//    Order order;

    @Autowired
    OrderRepo orderRepo;

    //create order
    @Transactional
    public Order placeOrder(Cart cart, UserRegister user, String address, String city, String postalcode, String area) throws RazorpayException {
        log.info("Entering placeOrder service method");

        Order order = new Order();
        order.setName(user.getFirstName() + " " + user.getLastName());
        order.setEmail(user.getEmail());
        order.setPhoneNumber(String.valueOf(user.getPhoneNumber()));
        order.setAddress(address);
        order.setCity(city);
        order.setPostalCode(postalcode);
        order.setArea(area);
        order.setAmount(cart.getTotalPrice());
        order.setOrderDate(new Date());
        order.setUserId(user.getUserId());
        order.setOrderStatus("Pending");

//        // Add CartItems to the Order and assign the order reference in each CartItem
//        List<CartItem> orderItems = new ArrayList<>();
//        for (CartItem item : cart.getItems()) {
//            CartItem orderItem = new CartItem();
//            orderItem.setProduct(item.getProduct());
//            orderItem.setQuantity(item.getQuantity());
//            orderItem.setPrice(item.getPrice());
//            orderItem.setOrder(order); // Set the order reference
//            orderItem.setCart(cart);   // Set the cart reference to avoid null constraint violation
//            orderItems.add(orderItem);
//        }
//        order.setCartItems(orderItems);



        List<CartItem> cartItems = cart.getItems();

        // Manually update each cart item’s ORDERID
        for (CartItem item : cartItems) {
            item.setOrder(order);
            cartItemRepo.save(item); // Explicitly update each CartItem
        }





        String razorPayOrderId = generateRazorpayOrder(cart.getTotalPrice(), user.getEmail());
        order.setRazorPayOrderId(razorPayOrderId);

        log.info("Order successfully generated with Razorpay ID: " + razorPayOrderId);
        return orderRepo.save(order);  // Saving order will cascade to CartItems if CascadeType.ALL
    }

    // Razorpay order generation method
    private String generateRazorpayOrder(Double totalPrice, String email) throws RazorpayException {
        log.info(" order genareteRazorPayOrder method eneterd");
        RazorpayClient razorpay = new RazorpayClient(razorPayKey, razorPaySecret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", totalPrice * 100);  // Amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", email);
        this.client = new RazorpayClient(razorPayKey, razorPaySecret);
        com.razorpay.Order razorpayOrder = razorpay.orders.create(orderRequest);
        log.info("end of the generateRazorpayOrder");
        return razorpayOrder.get("id");
    }



}
