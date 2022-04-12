package com.codesoom.assignment.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

}
