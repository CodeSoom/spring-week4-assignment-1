package com.codesoom.assignment.interfaces;

import com.codesoom.assignment.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findProducts();

    Product findProduct(Long id);

    Product createProduct(Product product);

//    Product updateProduct(Long id, Product product);
//
//    Product deleteProduct(Product product);
}
