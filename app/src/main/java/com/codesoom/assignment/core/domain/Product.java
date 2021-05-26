package com.codesoom.assignment.core.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 고양이 장난감
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String brand;

    private Integer price;

    @Builder
    public Product(String name, String brand, Integer price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public String toString() {
        return String.format("{ id: %s, name: %s, brand: %s, price: %s }", id, name, brand, price);
    }

}
