package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *  고양이 장난감 엔티티
 */
@Entity
public class CatToy {

    @Id
    @GeneratedValue
    private Long id;

    private String maker;

    private Integer price;

    private String imagePath;

    public CatToy() {
    }

    public CatToy(Long id) {
        this.id = id;
    }

    public CatToy(String maker, Integer price, String imagePath) {
        this.maker = maker;
        this.price = price;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public String getMaker() {
        return maker;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }
}
