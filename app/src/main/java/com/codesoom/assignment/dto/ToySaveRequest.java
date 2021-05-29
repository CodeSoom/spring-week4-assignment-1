package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Toy;

public class ToySaveRequest {
    private String name;
    private String brand;
    private Double price;
    private String imgUrl;

    public ToySaveRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Toy toEntity() {
        return new Toy.Builder(this.name, this.brand)
                .price(this.price)
                .imgUrl(this.imgUrl)
                .build();
    }
}
