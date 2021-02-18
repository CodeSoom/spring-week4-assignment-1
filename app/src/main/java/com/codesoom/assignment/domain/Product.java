package com.codesoom.assignment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private int price;
    private String image;

    public Product update(Product source) {
        this.name = source.getName();
        this.maker = source.getMaker();
        this.price = source.getPrice();
        this.image = source.getImage();
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "{ id = %s, name = %s, maker = %s, price = %d, image = %s}"
                , id, name, maker, price, image);
    }
}
