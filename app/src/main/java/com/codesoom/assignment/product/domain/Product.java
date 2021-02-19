package com.codesoom.assignment.product.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 상품 정보.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    /** 상품 식별자 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 상품명 */
    private String name;

    /** 상품제조사 */
    private String maker;

    /** 상품가격 */
    private int price;

    /** 상품이미지 */
    private String imageUrl;

    @Builder
    private Product(Long id, String name, String maker, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static Product of(String name, String maker, int price, String imageUrl) {
        return Product.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .imageUrl(imageUrl)
                .build();
    }

    /**
     * 상품의 정보를 갱신하고 상품을 리턴합니다.
     * @param name 상품명
     * @param maker 상품제조사
     * @param price 상품가격
     * @param imageUrl 상품이미지
     * @return 갱신된 상품 정보
     */
    public Product update(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
        return this;
    }
}
