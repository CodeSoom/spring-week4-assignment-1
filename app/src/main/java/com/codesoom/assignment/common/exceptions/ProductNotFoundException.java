package com.codesoom.assignment.common.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 상품을 찾을 수 없는 경우에 던집니다.
 */
public class ProductNotFoundException extends RuntimeException{

    private Logger log = LoggerFactory.getLogger(ProductNotFoundException.class);

    private String message = "존재하지 않는 상품이기 때문에 찾을 수 없습니다.";

    public ProductNotFoundException(Long id) {
        log.error(String.format("ID = %d 인 상품을 찾을 수 없습니다.", id));
    }

}
