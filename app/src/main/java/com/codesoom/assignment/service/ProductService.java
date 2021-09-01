package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService  {

    Product register(Product product);

    Optional<Product> getProduct(Long id);

    List<Product> getProducts();

    void delete(Long id);


}
