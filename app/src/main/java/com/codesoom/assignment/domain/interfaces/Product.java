package com.codesoom.assignment.domain.interfaces;

import java.math.BigDecimal;

public interface Product {
    String name();
    Producer producer();
    BigDecimal price();
}
