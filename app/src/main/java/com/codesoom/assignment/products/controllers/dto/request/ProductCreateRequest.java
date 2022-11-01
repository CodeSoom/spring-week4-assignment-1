package com.codesoom.assignment.products.controllers.dto.request;

import com.codesoom.assignment.products.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateRequest {
    private final String name;
    private final String maker;
    private final int price;
    private final String imgUrl;

    @Builder
    public ProductCreateRequest(String name, String maker, int price, String imgUrl) {
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
