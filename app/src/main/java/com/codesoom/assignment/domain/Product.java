package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private Long price;
    private String imagePath;

    public Product() {

    }

    public Product(String name, String maker, Long price, String imagePath) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imagePath = imagePath;
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

    public Long getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void changeProductInfo(Product source) {
        name = source.getName();
        maker = source.getMaker();
        price = source.getPrice();
        imagePath = source.getImagePath();
    }

    @Override
    public String toString() {
        return String.format(
                "{id=%s, name=%s, maker=%s, price=%d, imagePath=%s}",
                id, name, maker, price, imagePath);
    }

    public boolean hasEqualContents(Product other) {
        if(name.compareTo(other.getName()) == 0 &&
        maker.compareTo(other.getMaker()) == 0 &&
        price == other.getPrice() &&
        imagePath.compareTo(other.getImagePath()) == 0) {
            return true;
        }
        return false;
    }
}
