package com.codesoom.assignment.products.controllers.dto.request;

import com.codesoom.assignment.products.domain.Product;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ProductCreateRequest {
    private String name;
    private String maker;
    private int price;
    private String imgUrl;

    private ProductCreateRequest() {}

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
