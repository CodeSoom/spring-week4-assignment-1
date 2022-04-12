package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * 상품 엔티티
 */
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String maker;

    private Integer price;

    private String imagePath;

    public Product() {
    }

    public Product(Long id) {
        this(id, null, null, null);
    }

    public Product(String maker, Integer price, String imagePath) {
        this(null, maker, price, imagePath);
    }

    public Product(Long id, String maker, Integer price, String imagePath) {
        this.id = id;
        this.maker = maker;
        this.price = price;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(maker, product.maker) && Objects.equals(price, product.price) && Objects.equals(imagePath, product.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maker, price, imagePath);
    }

    @Override
    public String toString() {
        return String.format("CatToy{id=%s, maker=%s, price=%d, imagePath=%s}", id, maker, price, imagePath);
    }
}
