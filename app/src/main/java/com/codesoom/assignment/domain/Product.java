package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Product 리소스
 */
@Entity
public final class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String maker;
    private String imageUrl;
    private Long price;

    public Product(
        final String name, final String maker,
        final String imageUrl, final Long price
    ) {
        this.name = name;
        this.maker = maker;
        this.imageUrl = imageUrl;
        this.price = price;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getPrice() {
        return price;
    }

    public Product update(final Product source) {
        name = source.name;
        maker = source.maker;
        imageUrl = source.imageUrl;
        return this;
    }
}
