package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String maker;
    private Long price;
    private String imageURL;

    public Product() {
    }

    public Product(ProductDto productDto) {
        this.name = productDto.getName();
        this.maker = productDto.getMaker();
        this.price = productDto.getPrice();
        this.imageURL = productDto.getImageURL();
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

    public Long getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Product update(Product p) {
        name = p.name;
        maker = p.maker;
        price = p.price;
        imageURL = p.imageURL;

        return this;
    }
}
