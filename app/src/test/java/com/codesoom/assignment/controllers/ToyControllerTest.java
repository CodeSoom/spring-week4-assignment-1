package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyShopService;
import com.codesoom.assignment.interfaces.HttpCrudController;
import com.codesoom.assignment.interfaces.Product;
import com.codesoom.assignment.interfaces.ShopService;
import org.junit.jupiter.api.*;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("ToyController 클래스")
class ToyControllerTest {
    private ShopService service;
    private HttpCrudController controller;
    private final List<Product> toys = new LinkedList<>();

    @BeforeEach
    void setUp() {
        service = new ToyShopService();
        controller = new ToyController(service);
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
                given(service.showToysAll()).willReturn(toys);
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
