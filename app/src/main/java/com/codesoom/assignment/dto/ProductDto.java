package com.codesoom.assignment.dto;

import lombok.Data;

/**
 * Domain Model 과 Presentation Layer 에서의 미스매치를 해결하기 위해 만들어진 DTO 이다.
 * Product 엔티티 객체를 그대로 Presentation Layer 에 공개하지 않고,
 * ProductDto 객체를 이용해 데이터를 주고 받는다.
 *
 * @link https://martinfowler.com/eaaCatalog/dataTransferObject.html
 */
@Data
public class ProductDto {
    private String name;
    private String maker;
    private int price;
    private String image;
}
