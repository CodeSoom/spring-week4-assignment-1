package com.codesoom.assignment.domain;

public class ToyDto {

    private Long id;
    private final String name;
    private final String maker;
    private final Integer price;
    private final String image;

    public ToyDto(String name, String maker, Integer price, String image) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
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

    public String getImage() {
        return image;
    }
}
