package com.codesoom.assignment.domain;


import org.springframework.util.StringUtils;

/**
 * 상품 변경 요청
 */
public interface ProductUpdateRequest {

    String getName();

    String getMaker();

    Integer getPrice();

    String getImageUrl();

    /**
     * 상품을 대체하고 반환합니다.
     *
     * @param product 대상 상품
     */
    default Product replaceProduct(Product product) {
        return product.changeName(getName())
                .changeMaker(getMaker())
                .changePrice(getPrice())
                .changeImagePath(getImageUrl());
    }

    /**
     * 상품 속성을 변경하고 반환합니다.
     *
     * @param product 대상 상품
     */
    default Product updateProduct(Product product) {
        if (StringUtils.hasText(getName())) {
            product.changeName(getName());
        }

        if (StringUtils.hasText(getMaker())) {
            product.changeMaker(getMaker());
        }

        if (getPrice() != null) {
            product.changePrice(getPrice());
        }

        if (StringUtils.hasText(getImageUrl())) {
            product.changeImagePath(getImageUrl());
        }
        return product;
    }
}
