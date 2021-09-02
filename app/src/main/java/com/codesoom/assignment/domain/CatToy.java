package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CatToy")
public class CatToy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String maker;
    private int price;
    private String imageUrl;

    public CatToy(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public CatToy() {
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String maker() {
        return maker;
    }

    public int price() {
        return price;
    }

    public String imageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatToy catToy = (CatToy) o;
        return price == catToy.price && Objects.equals(id, catToy.id) && Objects.equals(name, catToy.name) && Objects.equals(maker, catToy.maker) && Objects.equals(imageUrl, catToy.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imageUrl);
    }
}
