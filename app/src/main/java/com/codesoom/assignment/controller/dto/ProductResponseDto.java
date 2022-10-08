package com.codesoom.assignment.controller.dto;

import com.codesoom.assignment.domain.Product;

/**
 * 상품 응답으로 사용합니다.
 */
public class ProductResponseDto {
    private final Long id;
    private final String name;
    private final String maker;
    private final int price;
    private final String imageUrl;

    public ProductResponseDto(Long id, String name, String maker, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(product.getId(),
            product.getName(),
            product.getMaker(),
            product.getPrice(),
            product.getImageUrl()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
