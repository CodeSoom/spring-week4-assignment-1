package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
@lombok.Generated
public class ProductCommand {

    @lombok.Generated
    @Getter
    @Builder
    @ToString
    public static class Register {
        private final Long id;

        private final String name;

        private final String maker;

        private final Long price;

        private final String imageUrl;

        public Product toEntity() {
            return Product.builder()
                    .id(id)
                    .name(name)
                    .maker(maker)
                    .price(price)
                    .imageUrl(imageUrl)
                    .build();
        }

    }
}
