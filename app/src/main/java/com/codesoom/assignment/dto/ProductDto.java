package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;

import java.math.BigDecimal;

/**
 * 상품을 저장/수정할 때, 상품의 정보를 전달하는 객체
 * */
public class ProductDto {

    private String name; // 상품 이름

    private String maker; // 상품 제작자

    private BigDecimal price; // 상품 가격

    private String imageUrl; // 상품 이미지 주소

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductDto(ProductDtoBuilder productDtoBuilder) {
        this.name = productDtoBuilder.name;
        this.maker = productDtoBuilder.maker;
        this.price = productDtoBuilder.price;
        this.imageUrl = productDtoBuilder.imageUrl;
    }

    public static class ProductDtoBuilder {
        private String name;
        private String maker;
        private BigDecimal price;
        private String imageUrl;

        public ProductDto.ProductDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductDto.ProductDtoBuilder maker(String maker) {
            this.maker = maker;
            return this;
        }

        public ProductDto.ProductDtoBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductDto.ProductDtoBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(this);
        }
    }

    /**
     * ProductDto의 상품 정보들을 Product에 담아서 반환한다.
     * */
    public Product toEntity() {
        return new Product.ProductBuilder()
                .name(name)
                .maker(maker)
                .price(price)
                .imageUrl(imageUrl)
                .build();
    }
}
