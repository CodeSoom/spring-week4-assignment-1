package com.codesoom.assignment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String maker;
    private int price;
    private String image;

    public Product(Long id, String name, String maker, int price, String image) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    public static Product insert(String name, String maker, int price, String image) {
        Product product = new Product();
        product.name = name;
        product.maker = maker;
        product.price = price;
        product.image = image;
        return product;
    }

    public void update(String name, String maker, int price, String image) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }
}
