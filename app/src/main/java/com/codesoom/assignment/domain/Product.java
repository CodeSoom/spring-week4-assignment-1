package com.codesoom.assignment.domain;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private String maker;

    private int price;

    @Lob
    private String imgUrl;

    public void update(Product product) {
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
    }
}
