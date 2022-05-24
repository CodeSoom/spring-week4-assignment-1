package com.codesoom.assignment.domain;

import com.codesoom.assignment.interfaces.Money;

import java.math.BigDecimal;

public class Won implements Money {
    private final BigDecimal value;

    public Won(BigDecimal value){
        this.value = value;
    }

    @Override
    public BigDecimal value() {
        return value;
    }
}
