package com.codesoom.assignment.toy.domain.dto;

public class ProductRequest {
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
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
