package com.codesoom.assignment;

public class Toy {
    private final Long id;
    private final String name;
    private final String maker;
    private final int price;
    private final String imageUrl;

    public Toy(Long id, String name, String maker, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
