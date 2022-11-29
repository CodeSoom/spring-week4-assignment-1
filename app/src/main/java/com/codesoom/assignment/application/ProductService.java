package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product Product);

    void deleteById(Long id);

//    Product update(Long id, Product toy);
}
