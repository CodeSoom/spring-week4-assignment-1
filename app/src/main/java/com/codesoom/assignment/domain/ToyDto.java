package com.codesoom.assignment.domain;

import lombok.Getter;

import java.math.BigDecimal;


/**
 * 장난감 등록/수정 시 사용할 DTO 클래스 입니다.
 */
@Getter
public class ToyDto {

    private String name;

    private String maker;

    private BigDecimal price;

    private String image;

    public ToyDto(String name, String maker, BigDecimal price, String image) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    public Toy toEntity() {
        return Toy.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .image(image)
                .build();
    }

}
