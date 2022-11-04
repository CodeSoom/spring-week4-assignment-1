package com.codesoom.assignment.products.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private int price;
    private String imgUrl;

    protected Product() {}

    @Builder
    public Product(Long id, String name, String maker, int price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public void update(Product updateProduct) {
        updateName(updateProduct);
        updateMarker(updateProduct);
        updatePrice(updateProduct);
        updateImgUrl(updateProduct);
    }

    private void updateImgUrl(Product updateProduct) {
        if (updateProduct.getImgUrl() != null) {
            this.imgUrl = updateProduct.getImgUrl();
        }
    }

    private void updatePrice(Product updateProduct) {
        if (updateProduct.getPrice() != 0) {
            this.price = updateProduct.getPrice();
        }
    }

    private void updateMarker(Product updateProduct) {
        if (updateProduct.getMaker() != null) {
            this.maker = updateProduct.getMaker();
        }
    }

    private void updateName(Product updateProduct) {
        if (updateProduct.getName() != null) {
            this.name = updateProduct.getName();
        }
    }
}
