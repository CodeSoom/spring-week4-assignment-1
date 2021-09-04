package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class CatToy {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    public CatToy() {
    }

    public void update(CatToy catToy) {
        this.name = catToy.getName();
        this.maker = catToy.getMaker();
        this.price = catToy.getPrice();
        this.imageUrl = catToy.getImageUrl();
    }
}
