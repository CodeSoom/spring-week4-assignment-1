package com.codesoom.assignment.utils;

import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exceptions.InvalidInputException;

import java.util.List;

public class ProductDtoValidator {

    public static void validate(ProductDto dto) {
        validateName(dto.getName());
        validateMaker(dto.getMaker());
        validatePrice(dto.getPrice());
        validateCategories(dto.getCategoryNames());
    }

    private static void validateCategories(List<String> categoryNames) {
        if (categoryNames == null) {
            throw new InvalidInputException("최소 하나의 카테고리를 지정하셔야 합니다.");
        }
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

    public static void checksAllFieldsNull(ProductDto dto) {
        if (dto.getName() == null
                && dto.getMaker() == null
                && dto.getPrice() == null
                && dto.getImageUrl() == null
                && dto.getCategoryNames() == null) {

            throw new InvalidInputException("변경할 정보를 최소 한 개는 입력하셔야 합니다.");
        }
    }
}
