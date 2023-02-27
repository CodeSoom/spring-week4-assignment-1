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

    private String maker;
    private int price;
    private String img;

    @Builder
    public Product(Long id, String maker, int price, String img) {
        this.id = id;
        this.maker = maker;
        this.price = price;
        this.img = img;
    }

}
