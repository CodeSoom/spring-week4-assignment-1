package com.codesoom.assignment.toy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    public Product() {
    }

    @Builder
    public Product(Long id, String name, String maker, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

}
