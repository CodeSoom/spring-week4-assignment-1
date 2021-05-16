package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ProductRequest {

    @JsonProperty
    private String name;
    @JsonProperty
    private String maker;
    @JsonProperty
    private double price;
    @JsonProperty
    private String imageUrl;

    public Product toProduct() {
        return Product.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .imageUrl(imageUrl)
                .build();
    }
}
