package com.codesoom.assignment.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "maker")
    private String maker;

    public Product() {
    }

    public Product(String name, int price, String imageUrl, String maker) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.maker = maker;
    }

    public Product updateProduct(String name, int price, String imageUrl, String maker) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.maker = maker;
        return this;
    }
}

