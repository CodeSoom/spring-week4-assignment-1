package com.codesoom.assignment.fixture;

import com.codesoom.assignment.domain.Toy;
import org.springframework.test.web.servlet.RequestBuilder;

public class Given {
    public final Long savedId = 1L;
    public final Long unsavedId = 100L;
    public final String name = "장난감 칼";
    public final String maker = "코드숨";
    public final int price = 5000;
    public final String imageUrl = "https://cdn.shopify.com/s/files/1/0940/6942/products/DSC0243_800x.jpg";
    public final String updatePostfixText = "+";
    public final int updatePostfixNumber = 1;

    public Toy newToy() {
        return new Toy(savedId, name, maker, price, imageUrl);
    }

    public Toy modifiedToy(Long id) {
        return new Toy(
                id,
                name + updatePostfixText,
                maker + updatePostfixText,
                price + updatePostfixNumber,
                imageUrl + updatePostfixText
        );
    }
}
