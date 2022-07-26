package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class CatToy {

    private Long id;
    private String name;
    private String maker;
    private Long price;
    private String img_url;

    public CatToy(){

    }

    public CatToy(CatToy toy){
        this.name = toy.getName();
        this.maker = toy.getMaker();
        this.price = toy.getPrice();
        this.img_url = toy.getImg_url();
    }
    
    public CatToy(String name, String maker, Long price, String img_url) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.img_url = img_url;
    }
}
