package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 상품 정보.
 */
@Entity
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private Long price;

    private String imageUrl;

    @Builder
    public Product(String name, String maker, Long price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    /**
     * 주어진 product의 정보로 수정합니다.
     *
     * @param source 수정하고자 하는 product
     */
    public void update(Product source) {
        this.name = source.getName();
        this.maker = source.getMaker();
        this.price = source.getPrice();
        this.imageUrl = source.getImageUrl();
    }
}
