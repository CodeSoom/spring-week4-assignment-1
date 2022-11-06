package com.codesoom.assignment.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    private static final String MESSAGE_POSTFIX = "에 해당하는 카테고리는 존재하지 않습니다.";

    public CategoryNotFoundException(Long id) {
        super(id + MESSAGE_POSTFIX);
    }

    public CategoryNotFoundException(String categoryName) {
        super(categoryName + MESSAGE_POSTFIX);
    }
}
