package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CatToy {
    @Id
    private String name;
    private String maker;
    private Integer price;
    private String url;

    public CatToy() {
    }

    public CatToy(String name, String maker, Integer price, String url) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.url = url;
    }
}
