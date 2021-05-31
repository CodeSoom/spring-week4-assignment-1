package com.codesoom.assignment.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MAKER", columnDefinition = "VARCHAR(128)")
    private String maker;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "IMAGE_URL", columnDefinition = "CLOB")
    private String imageUrl;

    public Product() {
    }

    public Product(String name, String maker, Long price) {
        this(null, name, maker, price, null);
    }

    public Product(Long id, String name, String maker, Long price,
                   String imageUrl) {
        this.id = id;
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

    public Long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void change(String name, String maker, Long price, String imageUrl) {
        if (name != null) {
            this.name = name;
        }
        if (maker != null) {
            this.maker = maker;
        }
        if (price != null) {
            this.price = price;
        }
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
    }

    @Override
    public String toString() {
        return String.format("Product{id=%s,name=%s, maker=%s, price=%d, " +
                                     "imageUrl=%s}",
                             this.getId(),
                             this.getName(),
                             this.getMaker(),
                             this.getPrice(),
                             this.getImageUrl()
                            );
    }
}
