package com.codesoom.assignment.controller;

import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Service;

public class ProductDto {

    @Getter
    @Service
    @ToString
    public static class RequestParam {
        private Long id;

        private String name;

        private String maker;

        private Long price;

        private String imageUrl;
    }
}
