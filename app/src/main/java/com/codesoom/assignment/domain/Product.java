package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Domain 에서의 Entity와 RDB 에서의 Entity는 다름
 * 여기서는 두개의 역활을 하는 Entity로 사용
 */
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column
    private String name;

    @Setter
    @Column
    private String maker;

    @Setter
    @Column
    private Integer price;

    @Setter
    @Column
    private String imageUrl;

    protected Product() {};

    private Product(Builder builder) {
        name = builder.name;
        maker = builder.maker;
        price = builder.price;
        imageUrl = builder.imageUrl;
    }

    public static class Builder {
        // Required parameters(필수 인자)
        private final int price;
        private final String name;

        // Optional parameters - 선택적 인자는 기본값으로 초기화
        private String maker = "";
        private String imageUrl = "";

        public Builder(int price, String name) {
            this.price = price;
            this.name = name;
        }

        public Builder maker(String val) {
            maker = val;
            return this;
        }

        public Builder imageUrl(String val) {
            imageUrl = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }

    }


    @Override
    public String toString() {
        return String.format(
                "{ id = %s name = %s, maker = %s, price = %s, imageUrl = %s}",
                id,
                name,
                maker,
                price,
                imageUrl
        );
    }
}