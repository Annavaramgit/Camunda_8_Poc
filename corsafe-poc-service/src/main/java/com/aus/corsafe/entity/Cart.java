package com.aus.corsafe.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Entity
@Table(name="cart_info")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    private Integer userId;

    @Column(nullable = false)
    private Double totalPrice = (double) 0;

    public Cart(Integer userId) {
        this.userId = userId;
    }
    public Cart() {
    }

    // Method to update the total price
    public void updateTotalPrice() {
        this.totalPrice = items.stream().mapToDouble(CartItem::calculateTotalPrice).sum();
    }

}