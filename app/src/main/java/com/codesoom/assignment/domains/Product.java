package com.codesoom.assignment.domains;

import com.codesoom.assignment.enums.Category;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Product {

    private Long productId;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String name;

    private String maker;

    private Integer price;

    private String image;

    public Product(Long productId, Category category, String name, String maker, Integer price, String image) {
        this.productId = productId;
        this.category = category;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    public Long getProductId() {
        return productId;
    }
}
