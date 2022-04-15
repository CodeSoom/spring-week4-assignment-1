package com.codesoom.assignment.contexts;

import com.codesoom.assignment.domains.Product;
import com.codesoom.assignment.dto.ProductReqDto;
import com.codesoom.assignment.enums.Category;

public abstract class ContextProduct {

    private static final Category CATEGORY_TOY = Category.TOY;
    private static final String CAT_TOWER_NAME = "캣타워";
    private static final String MAKER = "캣러버스";
    private static final int CAT_TOWER_PRICE = 5000;
    private static final String CAT_TOWER_IMAGE = "https://cdn.imweb.me/thumbnail/20200825/b940aaa4583a4.jpg";

    protected Product generateCatTowerWithId() {
        return Product.builder()
                .productId(1L)
                .category(CATEGORY_TOY)
                .name(CAT_TOWER_NAME)
                .maker(MAKER)
                .price(CAT_TOWER_PRICE)
                .image(CAT_TOWER_IMAGE)
                .build();
    }

    protected Product generateCatTower() {
        return Product.builder()
                .category(CATEGORY_TOY)
                .name(CAT_TOWER_NAME)
                .maker(MAKER)
                .price(CAT_TOWER_PRICE)
                .image(CAT_TOWER_IMAGE)
                .build();
    }

    protected ProductReqDto generateCatTowerRequest() {
        return ProductReqDto.builder()
                .name(CAT_TOWER_NAME)
                .maker(MAKER)
                .price(CAT_TOWER_PRICE)
                .image(CAT_TOWER_IMAGE)
                .build();
    }

    protected ProductReqDto generateCatTowerBadRequest() {
        return ProductReqDto.builder()
                .name(null)
                .maker(MAKER)
                .price(CAT_TOWER_PRICE)
                .image("")
                .build();
    }

    protected Product generateCatFishingRod() {
        return Product.builder()
                .category(CATEGORY_TOY)
                .name("고양이 낚시대")
                .maker(MAKER)
                .price(10000)
                .image("http://m.wooayeou.com/web/product/big/201706/269_shop1_759376.jpg")
                .build();
    }

}
