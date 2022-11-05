package com.codesoom.assignment.product.application.port.out;

import com.codesoom.assignment.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface QueryProductPort {
    List<Product> findAll();

    Optional<Product> findById(Long id);
}
