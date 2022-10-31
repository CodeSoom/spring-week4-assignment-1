package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyService;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.exceptions.NegativeIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ToyController 클래스의")
public class ToyControllerTest {

    private ToyService service;
    private ToyController controller;

    @BeforeEach
    void setUp() {
        service = mock(ToyService.class);
        controller = new ToyController(service);
    }

    @Test
    @DisplayName("getToys()는 service의 getToys()를 호출된다.")
    void when_getToy_invoked_then_getToys_of_service_invoked() {
        given(service.getToys()).willReturn(anyList());

        controller.getToys();

        verify(service).getToys();
    }

    @Test
    @DisplayName("getToy()를 양수의 id로 호출하면 service의 getToy()가 호출된다.")
    void when_getToy_invoked_with_positive_id_then_getToy_of_service_invoked() {
        given(service.getToy(1L)).willReturn(any(Toy.class));

        controller.getToy(1L);

        verify(service).getToy(1L);
    }

    @Test
    @DisplayName("getToy()를 음수의 id로 호출하면 예외를 발생시킨다.")
    void when_getToy_invked_negative_id_then_getToy_of_service_invoked() {
        assertThatThrownBy(() -> controller.getToy(-1L))
                .isInstanceOf(NegativeIdException.class);
    }

}
