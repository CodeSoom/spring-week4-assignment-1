package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String maker;
    private int price;
    private String img;

    public void modifyProduct(String name, String maker, int price, String img) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.img = img;
    }

    @Builder
    public Product(Long id, String name,String maker, int price, String img) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.img = img;
    }

}
