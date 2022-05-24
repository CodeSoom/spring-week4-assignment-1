package com.codesoom.assignment.controllers;

import com.codesoom.assignment.interfaces.CrudController;
import com.codesoom.assignment.interfaces.Product;
import com.codesoom.assignment.interfaces.CrudService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ToyCrudController 클래스")
class ToyCrudControllerTest {
    @Mock
    private CrudService service;
    private CrudController controller;
    private final List<Product> toys = new LinkedList<>();

    @BeforeEach
    void setUp() {
        controller = new ToyCrudController(service);
    }

    @AfterEach
    void clear() {
        toys.clear();
    }

    @Nested
    @DisplayName("list 메소드는")
    class Describe_list {
        @Nested
        @DisplayName("만약 장난감이 존재하지 않는다면")
        class Context_without_existing_toy {
            @BeforeEach
            void setUp() {
                toys.clear();
                given(service.showAll()).willReturn(toys);
            }

            @Test
            @DisplayName("비어 있는 List를 반환한다")
            void it_returns_empty_list() {
                final List<Product> actual = controller.list();

                assertThat(actual).isEmpty();
            }
        }
    }
}
