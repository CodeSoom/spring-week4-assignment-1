package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    private Long id;
    private String maker;
    private int price;
    private String img;

    public ProductDto(Product entity){
        this.id = entity.getId();
        this.maker = entity.getMaker();
        this.price = entity.getPrice();
        this.img = entity.getImg();
    }
}
