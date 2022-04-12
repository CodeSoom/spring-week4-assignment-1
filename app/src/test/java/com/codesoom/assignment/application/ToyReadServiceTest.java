package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class ToyReadServiceTest {

    @InjectMocks
    private ToyReadServiceImpl service;

    @Mock
    private ToyRepository  repository;

    private static final Long EXIST_ID = 1L;
    private static final Long NOT_EXIST_ID = 100L;

    private static final Toy SAVED_TOY_1
            = Toy.builder().name("쥐돌이").maker("어쩌구컴퍼니").price(BigDecimal.valueOf(3000)).build();
    private static final Toy SAVED_TOY_2
            = Toy.builder().name("곰돌이").maker("어쩌구컴퍼니").price(BigDecimal.valueOf(3000)).build();

    private static final List<Toy> TOYS = List.of(SAVED_TOY_1, SAVED_TOY_2);

    @DisplayName("findAll은 모든 장난감을 반환한다.")
    @Test
    void findAllTest() {
        given(repository.findAll()).willReturn(TOYS);

        List<Toy> toys = service.findAll();

        verify(repository).findAll();
        assertThat(toys).isNotEmpty();
        assertThat(toys).hasSize(2);
    }

    @DisplayName("findById는 id에 해당하는 장난감을 반환한다.")
    @Test
    void findByIdTest() {
        given(repository.findById(eq(EXIST_ID))).willReturn(Optional.of(SAVED_TOY_1));

        Toy toy = service.findById(EXIST_ID);

        assertThat(toy).isNotNull();
        assertThat(toy.getName()).isEqualTo(SAVED_TOY_1.getName());
    }

    @DisplayName("findById는 존재하지 않는 id로 조회할 경우 예외를 던진다.")
    @Test
    void findByNotExistIdTest() {
        given(repository.findById(eq(NOT_EXIST_ID))).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(NOT_EXIST_ID))
                .isInstanceOf(ToyNotFoundException.class);
    }

}
