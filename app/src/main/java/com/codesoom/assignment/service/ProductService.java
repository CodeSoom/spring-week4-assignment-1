package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;

import java.util.Optional;

public interface ProductService  {

    Product register(Product product);

    Optional<Product> getProduct(Long id);

    Iterable<Product> getProducts();

    Product updateProduct(Long id, Product product);

    void delete(Long id);


}
