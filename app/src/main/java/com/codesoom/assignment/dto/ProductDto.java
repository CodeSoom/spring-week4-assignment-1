package com.codesoom.assignment.dto;

import com.codesoom.assignment.models.Product;
import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    protected ProductDto() {}

    private ProductDto(Builder builder) {
        name = builder.name;
        maker = builder.maker;
        price = builder.price;
        imageUrl = builder.imageUrl;
    }

    public static class Builder {
        // Required parameters(필수 인자)
        private final int price;
        private final String name;

        // Optional parameters - 선택적 인자는 기본값으로 초기화
        private String maker = "";
        private String imageUrl = "";

        public Builder(int price, String name) {
            this.price = price;
            this.name = name;
        }

        public Builder maker(String val) {
            maker = val;
            return this;
        }

        public Builder imageUrl(String val) {
            imageUrl = val;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(this);
        }
    }

    public Product toEntity() {
        return new Product
                .Builder(price, name)
                .maker(maker)
                .imageUrl(imageUrl)
                .build();
    }
}
