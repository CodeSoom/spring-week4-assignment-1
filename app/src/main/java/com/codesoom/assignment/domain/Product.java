package com.codesoom.assignment.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private Long price;

    private String imageUrl;

    public Product() { }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) &&
                name.equals(product.name) &&
                maker.equals(product.maker) &&
                price.equals(product.price) &&
                imageUrl.equals(product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imageUrl);
    }
}
