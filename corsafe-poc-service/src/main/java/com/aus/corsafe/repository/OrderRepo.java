package com.aus.corsafe.repository;

import com.aus.corsafe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {

    List<Order> findByUserId(Integer userId);

    Optional<Order> findByRazorPayOrderId(String razorPayOrderId);

}

