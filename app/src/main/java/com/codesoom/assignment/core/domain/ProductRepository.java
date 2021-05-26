package com.codesoom.assignment.core.domain;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product product);
}
