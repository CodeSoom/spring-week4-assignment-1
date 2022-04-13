package com.codesoom.assignment.domains;

import com.codesoom.assignment.enums.Category;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String name;

    private String maker;

    private Integer price;

    private String image;

    public Product() { }

    public Product(Category category, String name, String maker, Integer price, String image) {
        this.category = category;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getProductId(), product.getProductId()) && category == product.category && Objects.equals(name, product.name) && Objects.equals(maker, product.maker) && Objects.equals(price, product.price) && Objects.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), category, name, maker, price, image);
    }
}
