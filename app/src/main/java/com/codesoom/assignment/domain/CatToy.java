package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class CatToy {
    @Id
    private String name;
    private String maker;
    private Long price;
    private String img_url;

    public CatToy(String name, String maker, Long price, String img_url) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.img_url = img_url;
    }
}
