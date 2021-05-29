package com.codesoom.assignment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
public class Toy {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    @Min(0)
    private Double price;

    @Column(name = "img_url")
    private String imgUrl;

    public static class Builder {
        private final String name;
        private final String brand;

        private Double price = 0.0;
        private String imgUrl = "";

        public Builder(String name, String brand) {
            this.name = name;
            this.brand = brand;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder imgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
            return this;
        }

        public Toy build() {
            return new Toy(this);
        }
    }

    public Toy() {}

    private Toy(Builder builder) {
        this.name = builder.name;
        this.brand = builder.brand;
        this.price = builder.price;
        this.imgUrl = builder.imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
