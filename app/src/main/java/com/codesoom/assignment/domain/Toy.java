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

    @Column(name = "maker")
    private String maker;

    @Column(name = "price")
    @Min(0)
    private Double price;

    @Column(name = "image_url")
    private String imageUrl;

    public static class Builder {
        private final String name;
        private final String maker;

        private Long id = null;
        private Double price = 0.0;
        private String imageUrl = "";

        public Builder(String name, String maker) {
            this.name = name;
            this.maker = maker;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder imgUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Toy build() {
            return new Toy(this);
        }
    }

    public Toy() {}

    private Toy(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.maker = builder.maker;
        this.price = builder.price;
        this.imageUrl = builder.imageUrl;
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

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
