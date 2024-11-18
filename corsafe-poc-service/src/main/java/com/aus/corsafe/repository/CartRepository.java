package com.aus.corsafe.repository;

import com.aus.corsafe.entity.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

    Optional<Cart> findByUserId(Integer userId);
    Optional<Cart> findByCartId(Long id);


}