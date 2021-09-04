package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.codesoom.assignment.dto.ProductDto;

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

    public Product() {
    }

    public Product(final ProductDto productDto) {
        this.name = productDto.getName();
        this.maker = productDto.getMaker();
        this.imageUrl = productDto.getImageUrl();
        this.price = productDto.getPrice();
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
        price = source.price;
        return this;
    }
}
