package com.codesoom.assignment.support;

import com.codesoom.assignment.products.controllers.dto.request.ProductCreateRequest;
import com.codesoom.assignment.products.controllers.dto.request.ProductUpdateRequest;
import com.codesoom.assignment.products.domain.Product;

public enum ProductFixture {
    TOY_1("범냐옹1", "메이드인 코리아", 2000000, "https://avatars.githubusercontent.com/u/59248326"),
    TOY_2("범냐옹2", "메이드인 안양", 3000000, "https://avatars.githubusercontent.com/u/59248326"),
    TOY_3("범냐옹3", "메이드인 사을", 5000000, "https://avatars.githubusercontent.com/u/59248326"),
    ;

    private final String name;
    private final String maker;
    private final int price;
    private final String imgUrl;

    ProductFixture(String name, String maker, int price, String imgUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Product 생성() {
        return 생성(null);
    }

    public Product 생성(final Long id) {
        return Product.builder()
                .id(id)
                .name(name)
                .maker(maker)
                .price(price)
                .imgUrl(imgUrl)
                .build();
    }

    public ProductCreateRequest 등록_요청_데이터_생성() {
        return ProductCreateRequest.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .imgUrl(imgUrl)
                .build();
    }

    public ProductUpdateRequest 수정_요청_데이터_생성() {
        return ProductUpdateRequest.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .imgUrl(imgUrl)
                .build();
    }

    public String NAME() {
        return name;
    }

    public String MAKER() {
        return maker;
    }

    public int PRICE() {
        return price;
    }

    public String IMAGE() {
        return imgUrl;
    }
}
