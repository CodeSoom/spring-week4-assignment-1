package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Nested
@DisplayName("Toy 클래스는")
class DescribeToy {
    // TODO
    // 1. getter test
    // 2. setter test

    @Nested
    @DisplayName("getter가 호출되었을 경우")
    class DescribeWithGetter {

        @BeforeEach
        void toyFixtureSetUp() {

        }

        @Test
        @DisplayName("속성을 반환한다")
        void ItReturnsAttributes() {

        }
    }


}
