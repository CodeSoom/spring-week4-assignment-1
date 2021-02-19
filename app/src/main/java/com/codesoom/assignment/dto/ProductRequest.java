package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;

public class ProductRequest {

    private Product product;

    public ProductRequest() {
        product = Product.builder().build();
    }

    public void setName(String name) {
        product = product.builder()
                .name(name)
                .maker(product.getMaker())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public void setMaker(String maker) {
        product = product.builder()
                .name(product.getName())
                .maker(maker)
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public void setPrice(int price) {
        product = product.builder()
                .name(product.getName())
                .maker(product.getMaker())
                .price(price)
                .imageUrl(product.getImageUrl())
                .build();
    }

    public void setImageUrl(String imageUrl) {
        product = product.builder()
                .name(product.getName())
                .maker(product.getMaker())
                .price(product.getPrice())
                .imageUrl(imageUrl)
                .build();
    }

    public String getName() {
        return product.getName();
    }

    public String getMaker() {
        return product.getMaker();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }

    public Product getProduct() {
        return product;
    }

    //    /**
    //     * 상품 요청서를 사용해 상품 엔터티를 생성해 리턴합니다.
    //     */
    //    public Product toProduct() {
    //        return Product.builder()
    //                .name(product.getName())
    //                .maker(product.getMaker())
    //                .price(product.getPrice())
    //                .imageUrl(product.getImageUrl())
    //                .build();
    //    }

}
