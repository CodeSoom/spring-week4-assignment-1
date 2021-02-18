package com.codesoom.assignment.dto.request;

import com.codesoom.assignment.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequest {

    private String name;
    private String maker;
    private int price;
    private String image;

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .maker(this.maker)
                .price(this.price)
                .image(this.image)
                .build();
    }
}
