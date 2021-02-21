package com.codesoom.assignment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String maker;
    private int price;
    private String image;

}
