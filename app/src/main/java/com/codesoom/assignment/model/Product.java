package com.codesoom.assignment.model;

import com.codesoom.assignment.dto.ProductDTO;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "maker")
    private String maker;

    public Product() {
    }

    public Product(String name, int price, String imageUrl, String maker) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.maker = maker;
    }

    public Product(ProductDTO.CreateProduct createProduct) {
		this(createProduct.getName(), createProduct.getPrice(), createProduct.getImageUrl(), createProduct.getMaker());
    }

    public Product updateProduct(ProductDTO.UpdateProduct updateProduct) {
        this.name = updateProduct.getName();
        this.price = updateProduct.getPrice();
        this.imageUrl = updateProduct.getImageUrl();
        this.maker = updateProduct.getMaker();
        return this;
    }
}

