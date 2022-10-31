package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyService;
import com.codesoom.assignment.domain.ToyDto;
import com.codesoom.assignment.exceptions.InvalidToyDtoException;
import com.codesoom.assignment.exceptions.NegativeIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ToyController 클래스의")
public class ToyControllerTest {

    private static final String NAME = "NAME";
    private static final String MAKER = "MAKER";
    private static final Integer PRICE = 100;
    private static final String IMAGE = "http://...";

    private ToyService service;
    private ToyController controller;

    private static Stream<String> nullAndEmptyAndWhiteSpacedString() {
        return Stream.of(null, "", "   ");
    }

    private static Stream<Integer> nullAndNegativeInteger() {
        return Stream.of(null, -1);
    }

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
        given(service.getToy(1L)).willReturn(any(ToyDto.class));

        controller.getToy(1L);

        verify(service).getToy(1L);
    }

    @Test
    @DisplayName("getToy()를 음수의 id로 호출하면 예외를 발생시킨다.")
    void when_getToy_invoked_negative_id_then_exception_thrown() {
        assertThatThrownBy(() -> controller.getToy(-1L))
                .isInstanceOf(NegativeIdException.class);
    }

    @Test
    @DisplayName("create()를 호출하면 service의 create()가 호출된다.")
    void when_create_invoked_then_create_of_service_invoked() {
        ToyDto toyDto = new ToyDto(NAME, MAKER, PRICE, IMAGE);
        given(service.create(any(ToyDto.class))).willReturn(any(ToyDto.class));

        controller.create(toyDto);

        verify(service).create(any(ToyDto.class));
    }

    @ParameterizedTest(name = "create()를 {0}을 name으로 가지는 dto를 인자로 호출하면 예외를 발생시킨다.")
    @MethodSource("nullAndEmptyAndWhiteSpacedString")
    void when_create_invoked_with_dto_of_invalid_name_then_exception_thrown(String name) {
        ToyDto dto = new ToyDto(name, MAKER, PRICE, IMAGE);

        assertThatThrownBy(() -> controller.create(dto)).isInstanceOf(InvalidToyDtoException.class);
    }

    @ParameterizedTest(name = "create()를 {0}을 maker로 가지는 dto를 인자로 호출하면 예외를 발생시킨다.")
    @MethodSource("nullAndEmptyAndWhiteSpacedString")
    void when_create_invoked_with_dto_of_invalid_maker_then_exception_thrown(String maker) {
        ToyDto dto = new ToyDto(NAME, maker, PRICE, IMAGE);

        assertThatThrownBy(() -> controller.create(dto)).isInstanceOf(InvalidToyDtoException.class);
    }

    @ParameterizedTest(name = "create()를 {0}를 price로 가지는 dto를 인자로 호출하면 예외를 발생시킨다.")
    @MethodSource("nullAndNegativeInteger")
    void when_create_invoked_with_dto_of_negative_price_then_exception_thrown(Integer price) {
        ToyDto dto = new ToyDto(NAME, MAKER, price, IMAGE);

        assertThatThrownBy(() -> controller.create(dto)).isInstanceOf(InvalidToyDtoException.class);
    }

    @ParameterizedTest(name = "create()를 {0}을 image로 가지는 dto를 인자로 호출하면 예외를 발생시킨다.")
    @MethodSource("nullAndEmptyAndWhiteSpacedString")
    void when_create_invoked_with_dto_of_invalid_image_then_exception_thrown(String image) {
        ToyDto dto = new ToyDto(NAME, MAKER, PRICE, image);

        assertThatThrownBy(() -> controller.create(dto)).isInstanceOf(InvalidToyDtoException.class);
    }


}
