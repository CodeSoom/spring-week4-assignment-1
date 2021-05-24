package com.codesoom.assignment.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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

    @Lob
    @Column(name = "IMAGE", columnDefinition = "BLOB")
    private Byte[] image;

    public Product() {
    }

    public Product(String name, String maker, Long price) {
        this(null, name, maker, price, null);
    }

    public Product(Long id, String name, String maker, Long price,
                   Byte[] image) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
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

    public Byte[] getImage() {
        return image;
    }
}
