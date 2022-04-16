package com.codesoom.assignment.domain;

import com.codesoom.assignment.domain.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
}
