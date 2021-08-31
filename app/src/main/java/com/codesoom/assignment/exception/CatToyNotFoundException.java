package com.codesoom.assignment.exception;

public class CatToyNotFoundException extends RuntimeException{
    public static final String DEFAULT_MESSAGE = "식별자가 [%d]인 고양이 장난감을 찾을 수 없었습니다.";

    public CatToyNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
