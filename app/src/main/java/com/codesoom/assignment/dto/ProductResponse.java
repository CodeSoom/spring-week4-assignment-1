package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

    private Long id;

    private String name;

    private String maker;

    private int price;

    private String imageUrl;

    /**
     * 상품 엔터티를 상품 응답 Dto로 변환하고, 상품 응답 Dto를 리턴합니다.
     *
     * @param product 변환하고자 하는 상품 엔터티
     * @return 변환된 상품 응답 Dto
     */
    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .maker(product.getMaker())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public static List<ProductResponse> listOf(List<Product> products) {
        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

}
