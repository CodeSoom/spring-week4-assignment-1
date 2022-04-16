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

    protected Product() {
    }

    private Product(String name, String maker, Integer price, String imagePath) {
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

    public Product changeName(String name) {
        this.name = name;
        return this;
    }

    public Product changeMaker(String maker) {
        this.maker = maker;
        return this;
    }

    public Product changePrice(Integer price) {
        this.price = price;
        return this;
    }

    public Product changeImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder  {

        private String name;

        private String maker;

        private Integer price;

        private String imagePath;

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setMaker(String maker) {
            this.maker = maker;
            return this;
        }

        public ProductBuilder setPrice(Integer price) {
            this.price = price;
            return this;
        }

        public ProductBuilder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Product build() {
            return new Product(name, maker, price, imagePath);
        }
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
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "Product{id=%s, name=%s, maker=%s, price=%d, imagePath=%s}", id, name, maker, price, imagePath);
    }
}
