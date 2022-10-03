package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.ProductCommand;
import com.codesoom.assignment.domain.ProductInfo;

import java.util.List;

public interface ProductService {

    List<ProductInfo> getProducts();

    ProductInfo getProduct(Long id);

    ProductInfo createProduct(ProductCommand.Register command);

    ProductInfo updateProduct(ProductCommand.Register command);

    void deleteProduct(Long id);
}

