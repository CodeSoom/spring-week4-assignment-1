package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product save(Product Product);

    List<Product> findAll();

    Optional<Product> findById(Long id);

    void deleteById(Long id);
}
