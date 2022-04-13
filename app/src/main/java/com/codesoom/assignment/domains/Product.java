package com.codesoom.assignment.domains;

import com.codesoom.assignment.enums.Category;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String name;

    private String maker;

    private Integer price;

    private String image;

    public Product() { }

    public Product(Category category, String name, String maker, Integer price, String image) {
        this.category = category;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }
}
