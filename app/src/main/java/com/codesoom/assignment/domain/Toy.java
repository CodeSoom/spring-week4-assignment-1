package com.codesoom.assignment.domain;

import java.math.BigDecimal;

public class Toy {

    private Long id;
    private String name;
    private String maker;
    private BigDecimal price;
    private String image;

    protected Toy() {
    }

    private Toy(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.maker = builder.maker;
        this.price = builder.price;
        this.image = builder.image;
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

    public String getImage() {
        return image;
    }

    public static class Builder {

        private Long id;
        private String name;
        private String maker;
        private BigDecimal price;
        private String image;

        public Builder(Long id, String name, String maker, BigDecimal price, String image) {
            this.id = id;
            this.name = name;
            this.maker = maker;
            this.price = price;
            this.image = image;
        }

        public Toy build() {
            return new Toy(this);
        }
    }
}
