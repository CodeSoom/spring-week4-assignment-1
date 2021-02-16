package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 고양이 장남간 가게의 상품 엔티티
 * @Author wenodev
 */

@NoArgsConstructor
@Getter
@Table(name = "soom_product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String maker;

    @Column
    private int price;

    @Column
    private String imgName;

    @Builder
    public Product(String name, String maker, int price, String imgName) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imgName = imgName;
    }

}
