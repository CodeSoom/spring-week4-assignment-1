package com.codesoom.assignment.exceptions;

public class ToyNotFoundException extends RuntimeException {
    public ToyNotFoundException(Long id) {
        super(id + "에 해당하는 장난감 제품이 없습니다.");
    }
}
