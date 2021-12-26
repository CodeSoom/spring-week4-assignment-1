package com.codesoom.assignment.domain;

import java.util.ArrayList;
import java.util.List;

public class ProductTestCase {
    private static List<Product> products = null;

    private ProductTestCase() {
    }

    public static List<Product> getTestProducts(int productCount) {
        if (products == null) {
            products = new ArrayList<>();
            for (int i = 0; i < productCount; i++) {
                products.add(new Product(0L, "catProduct" + i, "maker" + i, 100L, "product" + i + ".jpg"));
            }
        }
        return products;
    }

}
