package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;

public interface ProductUpdateService {

    Product update(Long id, ProductDto productDto);

}
