package com.codesoom.assignment.product.ui.dto;

import com.codesoom.assignment.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

/**
 * 상품의 응답 정보.
 */
@Getter
public class ProductResponseDto {
    /** 상품 식별자. */
    private Long id;

    /** 상품명. */
    private String name;

    /** 상품제조사. */
    private String maker;

    /** 상품가격. */
    private int price;

    /** 상품 이미지. */
    private String imageUrl;

    /**
     * 상품정보 응답 생성자.
     *
     * @param id 상품 식별자
     * @param name 상품명
     * @param maker 상품제조사
     * @param price 상품가격
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
     * 상품 정보로 응답용 상품 정보 명세서를 생성해 리턴합니다.
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

    /**
     *  Product가 동등한 객체라면 true를 리턴하고, 그렇지 않다면 false를 리턴합니다.
     */
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

    /**
     * 상품 객체의 해쉬 정보를 리턴합니다.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
