package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class Product {
    public Product() {}

    @Id
    private long id;
    private String name;
    private String maker;
    private int price;
    private String imagePath;

    @Builder
    public Product(String name, String maker, int price, String imagePath) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imagePath = imagePath;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void update(String name, String maker, int price, String imagePath) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imagePath = imagePath;
    }
}
