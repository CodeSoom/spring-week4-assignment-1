package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String maker;
    private int price;
    private String img;

    @Builder
    public ProductDto(Long id,String name,String maker , int price , String img){
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.img = img;
    }

    public ProductDto(Product entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.maker = entity.getMaker();
        this.price = entity.getPrice();
        this.img = entity.getImg();
    }
}
