package com.codesoom.assignment.application.query;

import com.codesoom.assignment.domain.ProductInfo;

import java.util.List;

public interface ProductQueryService {

    List<ProductInfo> getProducts();

    ProductInfo getProduct(Long id);
}

