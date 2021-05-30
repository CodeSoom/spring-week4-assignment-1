package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Toy;

public class ToySaveRequest {
    private String name;
    private String maker;
    private Double price;
    private String imageUrl;

    public ToySaveRequest() {}

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Toy toEntity() {
        return new Toy.Builder(this.name, this.maker)
                .price(this.price)
                .imgUrl(this.imageUrl)
                .build();
    }

    public Toy toEntityWithId(Long id) {
        return new Toy.Builder(this.name, this.maker)
                .id(id)
                .price(this.price)
                .imgUrl(this.imageUrl)
                .build();
    }
}
