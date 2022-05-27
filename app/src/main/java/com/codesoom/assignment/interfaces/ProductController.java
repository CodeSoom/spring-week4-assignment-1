package com.codesoom.assignment.interfaces;

import com.codesoom.assignment.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductController {
    
    List<Product> list();

    Product detail(Long id);

    Product create(Product product);

    void delete(Long id);
}
