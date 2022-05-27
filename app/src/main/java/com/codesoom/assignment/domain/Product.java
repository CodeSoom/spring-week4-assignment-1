package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private Long id;
    private String name;
    private String maker;
    private BigDecimal price;
    private String imagePath;

    public Product() {

    }

    public Product(String name, BigDecimal price, String maker, String imagePath) {
        this.name = name;
        this.price = price;
        this.maker = maker;
        this.imagePath = imagePath;
    }

    public static Product creatNewProduct(Long id, Product product) {
        Product newProduct = new Product(product.name, product.price, product.maker, product.imagePath);
        newProduct.id = id;
        return newProduct;
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

    public void changeName(String name) {
        this.name = name;
    }

    public void changeMaker(String maker) {
        this.maker = maker;
    }

    public void changePrice(BigDecimal price) {
        this.price = price;
    }

    public void changeImagePath(String imagePath) {
        this.imagePath = imagePath;
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
