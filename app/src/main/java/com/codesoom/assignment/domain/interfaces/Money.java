package com.codesoom.assignment.domain.interfaces;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * '돈' 인터페이스
 * <p>
 * All Known Implementing Classes:
 * </p>
 */
public interface Money {
    /**
     * Money 멤버변수 value 반환한다
     * <p>
     * @return Money 객체의 가치에 해당하는 BigDecimal 반환
     * </p>
     */
    BigDecimal value();
}
