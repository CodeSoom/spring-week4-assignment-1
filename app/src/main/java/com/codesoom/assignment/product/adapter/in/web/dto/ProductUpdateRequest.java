package com.codesoom.assignment.product.adapter.in.web.dto;

import com.codesoom.assignment.product.application.port.in.ProductUpdateCommand;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductUpdateRequest {
    private String name;
    private String maker;
    private int price;
    private String imgUrl;

    @Builder
    public ProductUpdateRequest(String name, String maker, int price, String imgUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    /**
     * 수정할 상품 정보를 Entity 객체로 복사하여 리턴합니다.
     *
     * @return 상품 수정 정보가 담긴 Entity 리턴
     */
    public ProductUpdateCommand toCommand() {
        return ProductUpdateCommand.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .imgUrl(imgUrl)
                .build();
    }
}
