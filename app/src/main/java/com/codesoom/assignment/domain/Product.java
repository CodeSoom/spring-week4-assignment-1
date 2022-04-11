package com.codesoom.assignment.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 id

    private String name; // 상품 이름

    private String maker; // 상품 제작자

    private BigDecimal price; // 상품 가격

    private String imageUrl; // 상품 이미지 주소

    public Product(ProductBuilder productBuilder) {
        this.id = productBuilder.id;
        this.name = productBuilder.name;
        this.maker = productBuilder.maker;
        this.price = productBuilder.price;
        this.imageUrl = productBuilder.imageUrl;
    }

    public Product() {

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

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static class ProductBuilder {
        private Long id;
        private String name;
        private String maker;
        private BigDecimal price;
        private String imageUrl;

        public ProductBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder maker(String maker) {
            this.maker = maker;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
