package com.codesoom.assignment.cattoy.exception;

public class CatToyInvalidFieldException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "필드 정보를 공백 혹은 Null으로 수정할 수 없습니다.";

    public CatToyInvalidFieldException() {
        super(DEFAULT_MESSAGE);
    }
}
