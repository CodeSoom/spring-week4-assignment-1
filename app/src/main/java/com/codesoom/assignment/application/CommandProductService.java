package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

public interface CommandProductService {

    Product createProduct(Product requestProduct);

    Product updateProduct(Long id, Product requestProduct);

    void deleteProduct(Long id);

    void deleteAll();
}
