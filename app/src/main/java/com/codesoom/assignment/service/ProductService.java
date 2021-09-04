package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;

import java.util.List;

public interface ProductService  {

    Product register(Product product);

    Product getProduct(Long id);

    List<Product> getProducts();

    Product updateProduct(Long id, Product product);

    void delete(Long id);

}
