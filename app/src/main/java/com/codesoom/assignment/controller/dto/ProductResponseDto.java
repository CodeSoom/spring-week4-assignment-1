package com.codesoom.assignment.controller.dto;

import com.codesoom.assignment.domain.Product;

/**
 * 상품 응답으로 사용합니다.
 */
public class ProductResponseDto {
    private final Product product;

    public ProductResponseDto(Product product) {
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
