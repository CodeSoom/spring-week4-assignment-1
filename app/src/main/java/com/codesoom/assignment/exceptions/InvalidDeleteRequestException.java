package com.codesoom.assignment.exceptions;

public class InvalidDeleteRequestException extends RuntimeException {
    public InvalidDeleteRequestException() {
        super("카테고리에 해당하는 제품이 존재하므로 삭제가 불가능합니다.");
    }
}
