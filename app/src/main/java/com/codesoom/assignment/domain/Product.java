package com.codesoom.assignment.domain;

import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private Long price;
    private String imageUrl;

    public Product() {
    }


    public Product(Long id, String name, String maker, Long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(Product sourceProduct) {
        this.name = sourceProduct.getName();
        this.maker = sourceProduct.getMaker();
        this.price = sourceProduct.getPrice();
        this.imageUrl = sourceProduct.getImageUrl();
    }

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void update(Product sourceProduct) {
        this.name = sourceProduct.getName();
        this.maker = sourceProduct.getMaker();
        this.price = sourceProduct.getPrice();
        this.imageUrl = sourceProduct.getImageUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.getName()) &&
                Objects.equals(maker, product.getMaker()) &&
                Objects.equals(price, product.getPrice()) &&
                Objects.equals(imageUrl, product.getImageUrl());

    }
}

