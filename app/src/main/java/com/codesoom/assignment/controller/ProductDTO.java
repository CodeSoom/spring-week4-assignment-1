package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;

public class ProductDTO {
    static ProductDTO from(Product product) {
        return new ProductDTO();
    }
}
