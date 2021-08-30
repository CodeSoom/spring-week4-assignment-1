package com.codesoom.assignment.exception;

/**
 * 장난감을 찾지 못한 경우 던지는 예외입니다.
 */
public class CatToyNotFoundException extends RuntimeException{

    public CatToyNotFoundException(String id) {
        super(String.format("id %s를 리스트에서 찾을 수 없습니다.", id));
    }
}
