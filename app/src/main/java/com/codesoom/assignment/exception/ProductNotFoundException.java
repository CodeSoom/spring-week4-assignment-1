package com.codesoom.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "고양이 장난감 정보를 찾을 수 없습니다.";
    private static final String MESSAGE_FORMAT = " Id %d에 해당하는 고양이 장난감이 존재하지 않습니다.";

    public ProductNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id, ExceptionMessageType messageType) {
        this(messageType.message() + String.format(MESSAGE_FORMAT, id));
    }
}
