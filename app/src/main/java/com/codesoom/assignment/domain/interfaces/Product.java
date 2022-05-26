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
     * Product의 멤버변수 name 반환한다
     * <p>
     * @return Product 객체의 이름에 해당하는 문자열 반환
     * </p>
     */
    String name();

    /**
     * Product의 멤버변수 producer 반환한다
     * <p>
     * @return Product 객체의 생산자에 해당하는 Producer 객체 반환
     * </p>
     */
    Producer producer();

    /**
     * Product의 멤버변수 price 반환한다
     * <p>
     * @return Product 객체의 가격 해당하는 BigDecimal 반환
     * </p>
     */
    BigDecimal price();
}
