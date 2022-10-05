package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a product Entity
 */

@Getter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String maker;

    private Long price;

    private String imageUrl;

    /**
     * returns a product instance using a builder pattern
     * @param name String to be initialized
     * @param maker String to be initialized
     * @param price Long to be initialized
     * @param imageUrl String to be initalized
     */

    @Builder
    public Product(String name, String maker, Long price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
