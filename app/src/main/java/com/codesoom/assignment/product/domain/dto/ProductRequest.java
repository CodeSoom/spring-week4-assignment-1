package com.codesoom.assignment.product.domain.dto;

import com.codesoom.assignment.product.application.InvalidProductRequest;
import com.codesoom.assignment.product.domain.Product;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@ToString
@Getter
public class ProductRequest {
    private final String name;
    private final String maker;
    private final Integer price;
    private final String imageUrl;

    public ProductRequest(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toProductEntity() {
        return Product.builder()
                .name(this.name)
                .maker(this.maker)
                .price(this.price)
                .imageUrl(this.imageUrl).build();
    }

    public void validate() {
        InvalidProductRequest invalidProductRequest = new InvalidProductRequest();

        if (StringUtils.isEmpty(this.name)) {
            invalidProductRequest.addValidation("name", "name is empty", "상품명은 필수입니다.");
        }
        if (StringUtils.isEmpty(this.maker)) {
            invalidProductRequest.addValidation("maker", "maker is empty", "제조사은 필수입니다.");
        }
        if (this.price == null || this.price < 0) {
            invalidProductRequest.addValidation("price", "price is empty", "가격은 필수입니다.");
        }

        if (invalidProductRequest.hasErrors()) {
            throw invalidProductRequest;
        }
    }
}
