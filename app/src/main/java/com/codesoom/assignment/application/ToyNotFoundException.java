package com.codesoom.assignment.application;

public class ToyNotFoundException extends RuntimeException{

    public ToyNotFoundException(Long id) {
        super(String.format("%s에 해당하는 장난감을 찾을 수 없습니다.", id));
    }

}
