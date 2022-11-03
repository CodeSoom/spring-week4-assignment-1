package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    Product getProduct(Long id);

    Product create(Product product, String categoryName);

    Product update(Long id, Product src);

    void delete(Long id);
}
