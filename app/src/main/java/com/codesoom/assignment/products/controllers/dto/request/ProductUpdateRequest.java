package com.codesoom.assignment.products.controllers.dto.request;

import com.codesoom.assignment.products.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductUpdateRequest {
    private String name;
    private String maker;
    private int price;
    private String imgUrl;

    private ProductUpdateRequest() {}

    @Builder
    public ProductUpdateRequest(String name, String maker, int price, String imgUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .imgUrl(imgUrl)
                .build();
    }
}
