package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;

public class ProductResponse {

    private Product product;

    public ProductResponse(Product product) {
        this.product = product;
    }

}
