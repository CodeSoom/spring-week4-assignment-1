package com.codesoom.assignment.utils;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exceptions.InvalidInputException;

public class ProductValidator {

    public static void validate(Product product) {
        validateName(product.getName());
        validateMaker(product.getMaker());
        validatePrice(product.getPrice());
    }

    private static void validatePrice(Integer price) {
        if (price == null) {
            throw new InvalidInputException("공백 문자를 제외한 price를 입력해주세요.");
        }
    }

    private static void validateMaker(String maker) {
        if (maker == null || maker.isBlank()) {
            throw new InvalidInputException("공백 문자를 제외한 maker를 입력해주세요.");
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("공백 문자를 제외한 name을 입력해주세요.");
        }
    }

    public static void checksAllFieldsNull(Product product) {
        if (product.getName() == null
                && product.getMaker() == null
                && product.getPrice() == null
                && product.getImageUrl() == null) {

            throw new InvalidInputException("변경할 정보를 최소 한 개는 입력하셔야 합니다.");
        }
    }
}
