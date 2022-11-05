package com.codesoom.assignment.products.controllers.dto.request;

import com.codesoom.assignment.products.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateRequest {
    private String name;
    private String maker;
    private int price;
    private String imgUrl;

    private ProductCreateRequest() {}

    @Builder
    public ProductCreateRequest(String name, String maker, int price, String imgUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    /**
     * 생성할 상품 정보를 Entity 객체로 복사하여 리턴합니다.
     * @return 상품 생성 정보가 담긴 Entity 리턴
     */
    public Product toEntity() {
        return Product.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .imgUrl(imgUrl)
                .build();
    }
}
