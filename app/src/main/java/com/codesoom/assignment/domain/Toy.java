package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 이름, 메이커, 가격, 이미지 상태를 가지고 있는 장난감 모델 클래스
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Toy {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private int price;
    private String imageUrl;

    public Toy(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
