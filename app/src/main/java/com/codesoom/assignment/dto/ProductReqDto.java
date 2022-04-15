package com.codesoom.assignment.dto;

import com.codesoom.assignment.domains.Product;
import com.codesoom.assignment.enums.Category;
import com.codesoom.assignment.exceptions.InvalidValueException;
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

    public void validation() {
        if (name == null || name.isEmpty() || maker == null || maker.isEmpty()
                || price == null || image == null || image.isEmpty()) {

            throw new InvalidValueException();
        }
    }
}
