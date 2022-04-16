package com.codesoom.assignment.factories;

import com.codesoom.assignment.domain.Product;

/**
 * 상품 생성 및 변경 담당
 */
public class ProductFactory {

    private ProductFactory() {
    }

    /**
     * 새로운 상품 반환
     *
     * @param name      상품명
     * @param maker     메이커
     * @param price     가격
     * @param imagePath 이미지 경로
     * @return 상품
     */
    public static Product getProduct(String name, String maker, Integer price, String imagePath) {

        return Product.builder()
                .setName(name)
                .setMaker(maker)
                .setPrice(price)
                .setImagePath(imagePath)
                .build();
    }

    /**
     * 비어있는 상품 반환
     */
    public static Product getEmptyProduct() {
        return Product.builder().build();
    }
}
