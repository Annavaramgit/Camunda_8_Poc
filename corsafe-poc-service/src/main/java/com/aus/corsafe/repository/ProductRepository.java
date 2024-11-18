package com.aus.corsafe.repository;

import com.aus.corsafe.entity.Products;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends CrudRepository<Products,Integer> {

    Products findByProductId(Integer id);
}
