package com.codesoom.assignment.domain;

import com.codesoom.assignment.application.ProductSaveRequest;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;


/**
 * 상품 등록/수정 시 사용할 DTO 클래스 입니다.
 * 사용자가 입력한 정보를 받기 위해 컨트롤러에서만 사용합니다.
 */
@Getter
public class ProductDto implements ProductSaveRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    private BigDecimal price;

    private String image;

    public ProductDto() {
    }

    public ProductDto(String name, String maker, BigDecimal price, String image) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    @Override
    public Product toEntity() {
        return Product.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .image(image)
                .build();
    }

}
