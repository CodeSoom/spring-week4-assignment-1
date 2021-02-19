package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.request.ProductRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private int price;
    private String image;

    @Builder
    public Product(String name, String maker, int price, String image) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    public Product update(ProductRequest request) {
        this.name = request.getName();
        this.maker = request.getMaker();
        this.price = request.getPrice();
        this.image = request.getImage();
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "{ id = %s, name = %s, maker = %s, price = %d, image = %s}"
                , id, name, maker, price, image);
    }
}
