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

    //    /**
    //     * 상품 엔터티로 상품 응답서를 생성해 리턴합니다.
    //     *
    //     * @param product 상품 엔터티
    //     * @return 생성된 상품 응답서
    //     */
    //    public static ProductResponse of(Product product) {
    //        return ProductResponse.builder()
    //                .id(product.getId())
    //                .name(product.getName())
    //                .maker(product.getMaker())
    //                .price(product.getPrice())
    //                .imageUrl(product.getImageUrl())
    //                .build();
    //    }

}
