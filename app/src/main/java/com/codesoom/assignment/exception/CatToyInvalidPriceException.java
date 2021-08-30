package com.codesoom.assignment.exception;

public class CatToyInvalidPriceException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "가격은 0원 이상이어야 합니다. 현재 입력 가격: [%d]";

    public CatToyInvalidPriceException(Long price) {
        super(String.format(DEFAULT_MESSAGE, price));
    }
}
