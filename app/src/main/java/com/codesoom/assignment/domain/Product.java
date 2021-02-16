package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private Integer price;

    @Lob
    private String imageUrl;

    public Product() {
    }

    public Product(Long id, String name, String maker, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product update(ProductDto productDto) {
        this.name = productDto.getName();
        this.maker = productDto.getMaker();
        this.price = productDto.getPrice();
        this.imageUrl = productDto.getImageUrl();
        return this;
    }
}
