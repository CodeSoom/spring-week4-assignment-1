package com.codesoom.assignment.domain;

import lombok.AccessLevel;
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

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private double price;
    private String image;

    @Builder
    public Product(Long id, String name, String maker, double price, String image) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }
}
