package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Product {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    private String maker;

    @Setter
    private Integer price;

    @Setter
    private String image;

    @Builder
    public Product(String name, String maker, Integer price, String image) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }
}
