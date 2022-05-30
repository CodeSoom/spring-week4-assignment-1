package com.codesoom.assignment.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private Long id;
    private String name;
    private String maker;
    private BigDecimal price;
    private String imagePath;


    public Product(Long id, String name, BigDecimal price, String maker, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.maker = maker;
        this.imagePath = imagePath;
    }

    public Long id() {
        return this.id;
    }

    public static Product creatNewProduct(Long id, Product product) {
        return new Product(id, product.name, product.price, product.maker, product.imagePath);
    }

    public boolean checkMyId(Long id) {
        return Long.compare(this.id, id) == 0 ? true : false;
    }

    public boolean isRegistered() {
        return id == null ? false : true;
    }

    public void changeProduct(Product product) {
        this.name = product.name;
        this.price = product.price;
        this.maker = product.maker;
        this.imagePath = product.imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(maker, product.maker) && Objects.equals(price, product.price) && Objects.equals(imagePath, product.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maker, price, imagePath);
    }
}
