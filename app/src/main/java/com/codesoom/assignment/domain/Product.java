package com.codesoom.assignment.domain;

import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String maker;
    private Long price;
    private String img_url;

    public Product(){

    }
    public Product(Product toy){
        this.name = toy.getName();
        this.maker = toy.getMaker();
        this.price = toy.getPrice();
        this.img_url = toy.getImg_url();
    }

    public Product(String name, String maker, Long price, String img_url) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.img_url = img_url;
    }

    public void updateProduct(Product toy){
        this.name = toy.getName();
        this.maker = toy.getMaker();
        this.price = toy.getPrice();
        this.img_url = toy.getImg_url();
    }
}
