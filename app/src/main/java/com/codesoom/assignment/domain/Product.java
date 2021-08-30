package com.codesoom.assignment.domain;

public class Product {

    private final String name;
    private final String maker;
    private final long price;
    private final String imageUrl;

    public Product(String name, String maker, long price, String imageUrl) {
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

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
