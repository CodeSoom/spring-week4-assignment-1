package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private int price;
    private String image;

    @Builder
    public Product(String name, String maker, int price, String image) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    /**
     * 상품을 수정하고, 수정된 상품을 리턴합니다.
     *
     * @param product 수정하고자 하는 상품 값
     * @return 수정된 상품
     */
    public Product update(Product product) {
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.image = product.getImage();

        return this;
    }
}
