package com.codesoom.assignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String name;
    private String maker;
    private BigDecimal price;
    private String imagePath;
}
