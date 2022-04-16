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
    private Integer price;
    private String imagePath;

    public Product() {

    }

    public Product(String name, String maker, Integer price) {
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    public Product(long id, String name, String maker, Integer price) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    public Product(String name, String maker, Integer price, String imagePath) {
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

    public Integer getPrice() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(maker, product.maker) &&
                Objects.equals(price, product.price) &&
                Objects.equals(imagePath, product.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imagePath);
    }
}
