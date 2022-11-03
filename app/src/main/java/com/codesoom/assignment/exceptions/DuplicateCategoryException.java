package com.codesoom.assignment.exceptions;

public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException(String categoryName) {
        super(categoryName + "은 이미 존재합니다.");
    }
}
