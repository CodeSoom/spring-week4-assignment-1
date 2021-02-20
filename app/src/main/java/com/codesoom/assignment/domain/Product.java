package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue
    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String maker;
    @Setter
    @Getter
    private int price;
    @Setter
    @Getter
    private String imageUrl;

    public Product() {
    }

    public Product(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    public Product(Long id, String name, String maker, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
