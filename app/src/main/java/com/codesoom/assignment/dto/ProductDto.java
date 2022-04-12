package com.codesoom.assignment.dto;

import com.codesoom.assignment.models.Product;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    protected ProductDto() {};

    public Product toEntity() {
        return new Product
                .Builder(price, name)
                .maker(maker)
                .imageUrl(imageUrl)
                .build();

    }

}
