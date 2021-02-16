package com.codesoom.assignment.domain;

import java.util.Objects;

public class Product {
    private ProductId id;

    private String name;
    private String maker;
    private String price;
    private String imageURL;

    public Product(ProductId id, String name, String maker, String price, String imageURL) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageURL = imageURL;
    }

    public ProductId productId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
