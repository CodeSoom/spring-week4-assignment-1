package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private Long price;

    private String imageUrl;

    @Builder
    public Product(String name, String maker, Long price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void update(Product source) {
        this.name = source.getName();
        this.maker = source.getMaker();
        this.price = source.getPrice();
        this.imageUrl = source.getImageUrl();
    }
}
