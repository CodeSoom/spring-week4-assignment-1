package com.codesoom.assignment.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
public class CatToy {
    @Id
    private String name;
    private String maker;
    private Integer price;
    private String url;

    protected CatToy() {
    }

    public CatToy(String name, String maker, Integer price, String url) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CatToy catToy = (CatToy) o;
        return Objects.equals(name, catToy.name) && Objects.equals(maker, catToy.maker) && Objects.equals(price, catToy.price) && Objects.equals(url, catToy.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maker, price, url);
    }
}
