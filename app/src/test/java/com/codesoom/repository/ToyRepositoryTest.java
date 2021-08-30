package com.codesoom.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ToyRepository 클래스")
class ToyRepositoryTest {

    private ToyRepository toyRepository = new ToyRepository();

    private final String[] TOY_TEST_NAME = {"toy1", "toy2", "toy3", "toy4", "toy5"};
    private final String[] TOY_TEST_MAKER = {"maker1", "maker2", "maker3", "maker4", "maker5"};


    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        
    }


}