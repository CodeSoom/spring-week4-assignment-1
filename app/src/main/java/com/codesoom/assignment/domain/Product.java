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
