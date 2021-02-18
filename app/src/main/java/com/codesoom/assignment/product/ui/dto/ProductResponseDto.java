package com.codesoom.assignment.product.ui.dto;

import com.codesoom.assignment.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

/**
 * 상품의 정보를 담은 응답 DTO.
 */
@Getter
public class ProductResponseDto {

    private Long id;

    private String name;

    private String maker;

    private int price;

    private String imageUrl;

    /**
     * 상품정보 응답 생성자.
     *
     * @param id       상품 식별자
     * @param name     상품명
     * @param maker    상품제조사
     * @param price    상품가격
     * @param imageUrl 상품이미지
     */
    @Builder
    public ProductResponseDto(Long id, String name, String maker, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    /**
     * 상품 정보를 응답 DTO로 변경하는 정적 팩토리 메서드.
     *
     * @param product 상품 entity
     */
    public static ProductResponseDto of(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .maker(product.getMaker())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductResponseDto)) {
            return false;
        }
        ProductResponseDto dto = (ProductResponseDto) o;
        return getId().equals(dto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
