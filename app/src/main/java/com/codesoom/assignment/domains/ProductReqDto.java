package com.codesoom.assignment.domains;

import com.codesoom.assignment.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReqDto {

    private String name;

    private String maker;

    private Integer price;

    private String image;

    public Product toProduct() {
        return Product.builder()
                .category(Category.TOY)
                .name(name)
                .maker(maker)
                .price(price)
                .image(image)
                .build();
    }

}
