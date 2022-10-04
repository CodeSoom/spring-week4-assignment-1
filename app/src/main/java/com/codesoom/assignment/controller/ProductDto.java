package com.codesoom.assignment.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@lombok.Generated
public class ProductDto {

    @Getter
    @Setter
    @ToString
    public static class RequestParam {
        private Long id;

        private String name;

        private String maker;

        private Long price;

        private String imageUrl;
    }
}
