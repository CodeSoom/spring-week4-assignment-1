package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductModel;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String maker;
    private int price;
    private String imageUrl;

    public Product(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product() {
    }

    public void changeProduct(ProductModel productModel) {
        if (productModel == null) {
            throw new IllegalArgumentException();
        }

        changeName(productModel.name());
        changeMaker(productModel.maker());
        changePrice(productModel.price());
        changeImageUrl(productModel.imageUrl());

    }

    private void changeName(String name) {
        if (StringUtils.hasLength(name)) {
            this.name = name;
        }
    }

    private void changeMaker(String maker) {
        if (StringUtils.hasLength(maker)) {
            this.maker = maker;
        }
    }

    private void changePrice(Integer price) {
        if (!Objects.isNull(price)) {
            this.price = price;
        }
    }

    private void changeImageUrl(String imageUrl) {
        if (StringUtils.hasLength(imageUrl)) {
            this.imageUrl = imageUrl;
        }
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String maker() {
        return maker;
    }

    public int price() {
        return price;
    }

    public String imageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product catToy = (Product) o;
        return price == catToy.price && Objects.equals(id, catToy.id) && Objects.equals(name, catToy.name) && Objects.equals(maker, catToy.maker) && Objects.equals(imageUrl, catToy.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imageUrl);
    }
}
