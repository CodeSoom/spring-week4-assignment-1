package com.codesoom.assignment.domain;

public class Product {

    private Long id;
    private String name;
    private String maker;
    private Long price;
    private String imageURI;

    public Product() {}

    public Product(Long id, String name, String maker, Long price, String imageURI) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageURI = imageURI;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
