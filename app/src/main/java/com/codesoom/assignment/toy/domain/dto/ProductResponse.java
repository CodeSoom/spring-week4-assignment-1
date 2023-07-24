package com.codesoom.assignment.toy.domain.dto;

import com.codesoom.assignment.toy.domain.Product;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    public ProductResponse() {
    }

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

}
