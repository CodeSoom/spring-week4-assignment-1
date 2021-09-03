package com.codesoom.assignment.domains;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private String maker;
    private String price;
    private String image;
}
