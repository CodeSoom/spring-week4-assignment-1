package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {
    public Long id;
    public String name;
    public String maker;
    public String price;
    public String imageURL;

    public ProductDTO(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("maker") String maker,
        @JsonProperty("price") String price,
        @JsonProperty("image_url") String imageURL
    ){
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageURL = imageURL;
    }

    static ProductDTO from(Product product) {
        return new ProductDTO(
            product.productId().id(),
            product.getName(),
            product.getMaker(),
            product.getPrice(),
            product.getImageURL()
        );
    }
}
