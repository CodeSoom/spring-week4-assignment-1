package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String productName;
    private String maker;
    private Long price;
    private String imageUrl;

    protected Product() {
    }

    public Product(String productName, String maker, Long price, String imageUrl) {
        this.productName = productName;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return String.format(
                "Product[id=%d, productName='%s', maker='%s', price=%d, imageUrl='%s']",
                id, productName, maker, price, imageUrl);
    }

}
