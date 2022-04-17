package com.codesoom.assignment.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


@Getter
@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String maker;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    protected Product() {
    }

    public Product(String name, String maker, BigDecimal price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(Long id, String name, String maker, BigDecimal price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product update(Product product) {
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        return this;
    }

    @Override
    public String toString() {
        return String.format("Product = { id: %s, name: %s, maker: %s, price: %s, image: %s }",
                id, name, maker, price, imageUrl);
    }

}
