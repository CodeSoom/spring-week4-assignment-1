package com.codesoom.assignment.domain;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String maker;

    private Integer price;

    private String imageUrl;

    public Product() {
    }

    @Builder
    private Product(Long id, String name, String maker, int price, String imageUrl) {
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

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void changeNameTo(String name) {
        this.name = name;
    }

    public void changeMakerTo(String maker) {
        this.maker = maker;
    }

    public void changePriceTo(Integer price) {
        this.price = price;
    }

    public void changeImageUrlTo(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
