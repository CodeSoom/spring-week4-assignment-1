package com.codesoom.assignment.domain.interfaces;

import java.math.BigDecimal;

/**
 * '상품' 인터페이스
 * <p>
 * All Known Implementing Classes:
 * Toy
 * </p>
 */
public interface Product {
    /**
     * '상품'의 이름을 반환한다
     * <p>
     * @return '상품'의 이름
     * </p>
     */
    String name();

    /**
     * '상품'의 '생산자'를 반환한다
     * <p>
     * @return '상품'의 생산자
     * </p>
     */
    Producer producer();

    /**
     * '상품'의 '가격'을 반환한다
     * <p>
     * @return '상품'의 가격
     * </p>
     */
    BigDecimal price();
}
