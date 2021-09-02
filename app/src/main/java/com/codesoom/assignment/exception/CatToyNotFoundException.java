package com.codesoom.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CatToyNotFoundException extends RuntimeException{

    private static final String MESSAGE = "고양이 장난감 정보를 찾을 수 없습니다.";

    public CatToyNotFoundException() {
        super(MESSAGE);
    }
}
