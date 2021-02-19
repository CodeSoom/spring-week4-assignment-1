package com.codesoom.assignment.dto.request;

import com.codesoom.assignment.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductRequest {

    private final String name;
    private final String maker;
    private final int price;
    private final String image;

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .maker(this.maker)
                .price(this.price)
                .image(this.image)
                .build();
    }
}
