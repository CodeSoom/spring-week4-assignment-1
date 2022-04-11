package com.codesoom.assignment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;


/**
 * 장난감 엔티티
 */
@Entity
public class Toy {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String maker;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String image;

    protected Toy() {
    }

    private Toy(Builder builder) {
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

        private String name;
        private String maker;
        private BigDecimal price;
        private String image;

        public Builder(String name, String maker, BigDecimal price) {
            this.name = name;
            this.maker = maker;
            this.price = price;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Toy build() {
            return new Toy(this);
        }

    }

}
