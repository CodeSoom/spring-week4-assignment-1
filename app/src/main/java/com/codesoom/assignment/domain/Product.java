package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Product {



    @Id
    @GeneratedValue
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

    public void setId(Long id) {
        this.id = id;
    }

    public Product(String name, String maker, Long price, String img_url) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.img_url = img_url;
    }

    public void updateItem(Product toy){
        this.name = toy.getName();
        this.maker = toy.getMaker();
        this.price = toy.getPrice();
        this.img_url = toy.getImg_url();
    }
}
