package com.codesoom.assignment.domain;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product find(Long id);
    Product save(Product product);
    Product update(Long id, Product product);
    Product remove(Long id);
}
