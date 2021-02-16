package com.codesoom.assignment.domain;

public class Product {
    private Long id;

    private String name;
    private String maker;
    private String price;
    private String imageURL;

    public Product(String name, String maker, String price, String imageURL) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
