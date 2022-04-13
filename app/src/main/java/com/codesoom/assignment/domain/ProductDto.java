package com.codesoom.assignment.domain;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;


/**
 * 상품 등록/수정 시 사용할 DTO 클래스 입니다.
 * 고객이 입력한 정보를 받아 엔티티로 변환하는 역할을 담당합니다.
 */
@Getter
public class ProductDto {

    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    private BigDecimal price;

    private String image;

    public ProductDto(String name, String maker, BigDecimal price, String image) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .image(image)
                .build();
    }

}
