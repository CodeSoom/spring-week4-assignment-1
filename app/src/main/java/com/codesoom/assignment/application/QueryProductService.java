package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

import java.util.List;

public interface QueryProductService {

    List<Product> getProducts();

    Product getProduct(Long id);
}
