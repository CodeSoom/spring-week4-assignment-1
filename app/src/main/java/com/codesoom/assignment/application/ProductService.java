package com.codesoom.assignment.application;


import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductList();

    Product getProduct(Long id);

    Product createProduct(ProductDto productDto);

    Product updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);
}

