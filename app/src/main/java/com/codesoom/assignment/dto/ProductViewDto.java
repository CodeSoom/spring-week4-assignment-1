package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;


/**
 * 상품 데이터 전송에 필요한 데이터 정의
 */
public class ProductViewDto {

    private final Product product;

    private ProductViewDto(Product product) {
        this.product = product;
    }

    /**
     * 상품을 받아 전송에 필요한 데이터 반환
     *
     * @param product 상품 엔티티
     */
    public static ProductViewDto from(final Product product) {
        return new ProductViewDto(product);
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

    public Integer getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImagePath();
    }
}
