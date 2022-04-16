package com.codesoom.assignment.factories;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductUpdateRequest;
import org.springframework.util.StringUtils;

/**
 * 상품 생성 및 변경 담당
 */
public class ProductFactory {

    private ProductFactory() {
    }

    /**
     * 새로운 상품 반환
     * @param name  상품명
     * @param maker 메이커
     * @param price 가격
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

    /**
     * 상품 속성을 변경하고 반환
     * @param product 대상 상품
     * @param updateRequest 변경될 상품 데이터
     */
    public static Product updateProduct(Product product, ProductUpdateRequest updateRequest) {
        if (StringUtils.hasText(updateRequest.getName())) {
            product.changeName(updateRequest.getName());
        }

        if (StringUtils.hasText(updateRequest.getMaker())) {
            product.changeMaker(updateRequest.getMaker());
        }

        if (updateRequest.getPrice() != null) {
            product.changePrice(updateRequest.getPrice());
        }

        if (StringUtils.hasText(updateRequest.getImageUrl())) {
            product.changeImagePath(updateRequest.getImageUrl());
        }
        return product;
    }

    /**
     * 상품을 대체하고 반환
     * @param product 대상 상품
     * @param replaceSource 대체될 상품 데이터
     */
    public static Product replaceProduct(Product product, ProductUpdateRequest replaceSource) {
        return product.changeName(replaceSource.getName())
                .changeMaker(replaceSource.getMaker())
                .changePrice(replaceSource.getPrice())
                .changeImagePath(replaceSource.getImageUrl());
    }
}
