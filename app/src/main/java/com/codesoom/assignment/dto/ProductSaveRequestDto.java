package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSaveRequestDto {
    private Long id;
    private String maker;
    private int price;
    private String img;

    @Builder
    public ProductSaveRequestDto(String maker , int price , String img){
        this.maker = maker;
        this.price = price;
        this.img = img;
    }

    public Product toEntity(){
        return Product.builder()
                .maker(maker)
                .price(price)
                .img(img)
                .build();
    }
}
