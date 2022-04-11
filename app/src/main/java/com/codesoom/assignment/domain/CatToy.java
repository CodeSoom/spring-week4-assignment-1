package com.codesoom.assignment.domain;

public class CatToy {
    Long id;
    String name;
    String maker;
    Long price;
    String imagePath;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public Long getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
