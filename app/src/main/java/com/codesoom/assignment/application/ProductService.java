package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts();

    ProductDto getProduct(Long id);

    ProductDto create(ProductDto dto);

    ProductDto update(Long id, ProductDto src);

    void delete(Long id);
}
