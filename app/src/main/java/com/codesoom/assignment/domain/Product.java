package com.codesoom.assignment.domain;

import java.util.Objects;

public class Product {

    private final String name;
    private final String maker;
    private final long price;
    private final String imageUrl;

    public Product(String name, String maker, long price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;

    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name)
            && Objects.equals(maker, product.maker) && Objects
            .equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maker, price, imageUrl);
    }
}
