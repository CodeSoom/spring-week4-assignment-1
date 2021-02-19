package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;

public class ProductResponse {

    private Product product;

    public ProductResponse(Product product) {
        this.product = product;
    }

    public Long getId() {
        return product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public String getMaker() {
        return product.getMaker();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }

}
