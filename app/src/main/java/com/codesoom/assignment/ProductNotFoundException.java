package com.codesoom.assignment;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(long id) {
        super("해당 아이디를 가진 등록된 장난감이 없습니다. ID : " + id);
    }
}
