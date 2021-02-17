package com.codesoom.assignment.product.ui.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품의 등록정보를 담은 요청 DTO.
 */
@Data
@NoArgsConstructor
public class ProductSaveRequestDto {
    private String name;

    private String maker;

    private int price;

    private String imageUrl;

    /**
     * 상품 등록 생성자.
     * @param name 상품명
     * @param maker 상품제조사
     * @param price 상품가격
     * @param imageUrl 상품이미지
     */
    public ProductSaveRequestDto(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
