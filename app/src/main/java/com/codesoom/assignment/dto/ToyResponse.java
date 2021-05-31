package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Toy;

public class ToyResponse {

    private Long id;
    private String name;
    private String maker;
    private Double price;
    private String imageUrl;

    public ToyResponse() {}

    public ToyResponse(Toy toy) {
        this.id = toy.getId();
        this.name = toy.getName();
        this.maker = toy.getMaker();
        this.price = toy.getPrice();
        this.imageUrl = toy.getImageUrl();
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

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
