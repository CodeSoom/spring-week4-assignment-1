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
     * '돈'의 가치를 반환한다
     * <p>
     * @return '돈'의 가치
     * </p>
     */
    BigDecimal value();
}
