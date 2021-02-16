package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private Integer price;

    @Lob
    private String img;

    private Product() {
    }

    public Product(Long id, String name, String maker, Integer price, String img) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.img = img;
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

    public String getImg() {
        return img;
    }
}
