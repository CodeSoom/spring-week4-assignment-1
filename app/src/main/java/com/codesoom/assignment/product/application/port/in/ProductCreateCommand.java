package com.codesoom.assignment.product.application.port.in;

import com.codesoom.assignment.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateCommand {
    private String name;
    private String maker;
    private int price;
    private String imgUrl;

    @Builder
    public ProductCreateCommand(String name, String maker, int price, String imgUrl) {
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
