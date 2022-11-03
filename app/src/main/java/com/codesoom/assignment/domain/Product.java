package com.codesoom.assignment.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private final List<ProductCategory> productCategoryList = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void addProductCategory(ProductCategory productCategory) {
        productCategoryList.add(productCategory);
    }

    public void update(Product src) {
        if (src.name != null) {
            this.name = src.name;
        }

        if (src.maker != null) {
            this.maker = src.maker;
        }

        if (src.price != null) {
            this.price = src.price;
        }

        if (src.imageUrl != null) {
            this.imageUrl = src.imageUrl;
        }
    }

}
