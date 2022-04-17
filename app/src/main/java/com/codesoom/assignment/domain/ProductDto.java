package com.codesoom.assignment.domain;

import com.codesoom.assignment.application.ProductSaveRequest;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;


/**
 * 상품 등록/수정 시 사용할 DTO 클래스 입니다.
 * 사용자가 입력한 정보를 받기 위해 컨트롤러에서만 사용합니다.
 */
public class ProductDto implements ProductSaveRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    private BigDecimal price;

    private String imageUrl;

    public ProductDto() {
    }

    public ProductDto(String name, String maker, BigDecimal price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getMaker() {
        return this.maker;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public String getImageUrl() {
        return this.imageUrl;
    }

}
