package com.codesoom.assignment.interfaces;

import com.codesoom.assignment.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductController {
    
    List<Product> getProducts();

    Optional<Product> getProduct(Long id);

    Product create(Product product);
}
