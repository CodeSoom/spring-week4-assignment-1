package com.codesoom.assignment.dto.ListDto;

import com.codesoom.assignment.domain.Product;
import lombok.Getter;

@Getter
public class ProductListDto {
    private Long id;
    private String maker;
    private int price;
    private String img;

    public ProductListDto(Product entity){
        this.id = entity.getId();
        this.maker = entity.getMaker();
        this.price = entity.getPrice();
        this.img = entity.getImg();
    }
}
