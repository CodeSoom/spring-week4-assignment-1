package com.codesoom.assignment.toy.domain.dto;

import com.codesoom.assignment.toy.domain.Product;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
