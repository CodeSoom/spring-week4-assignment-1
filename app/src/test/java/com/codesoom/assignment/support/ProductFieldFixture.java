package com.codesoom.assignment.support;

public enum ProductFieldFixture {
    TEST_EXIST(1L),
    TEST_NOT_EXIST(Long.MAX_VALUE),
    ;

    private final Long id;

    ProductFieldFixture(Long id) {
        this.id = id;
    }

    public Long ID() {
        return id;
    }
}
