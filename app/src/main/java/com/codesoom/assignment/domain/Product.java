package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
public class Product {
    private Long id;
    private String name;
    private String maker;
    private int price;
    private String image;

    @Builder
    public Product(String name, String maker, int price, String image) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    @Id
    @GeneratedValue
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format(
                "{ id = %s, name = %s, maker = %s, price = %d, image = %s}"
                , id, name, maker, price, image);
    }
}
