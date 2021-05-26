package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Toy;

public class ToySaveRequest {
    private String name;
    private String brand;
    private Double price;
    private String imgUrl;

    public static class Builder {
        private String name;
        private String brand;
        private Double price;
        private String imgUrl;

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

        public ToySaveRequest build() {
            return new ToySaveRequest(this);
        }
    }

    public ToySaveRequest() {}

    private ToySaveRequest(Builder builder) {
        this.name = builder.name;
        this.brand = builder.brand;
        this.price = builder.price;
        this.imgUrl = builder.imgUrl;
    }

    public Toy toEntity() {
        return new Toy
                .Builder(this.name, this.brand)
                .price(this.price)
                .imgUrl(imgUrl)
                .build();
    }
}
