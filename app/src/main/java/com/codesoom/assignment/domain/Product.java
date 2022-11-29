package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue
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

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getMaker() {
        return maker;
    }

    public Long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return String.format(
                "Product[id=%d, productName='%s', maker='%s', price=%d]",
                id, productName, maker, price);
    }
}
