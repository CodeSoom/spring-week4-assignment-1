package com.codesoom.assignment.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCommand {

    private String name;

    private String maker;

    private Integer price;

    private String imageUrl;
    
}
