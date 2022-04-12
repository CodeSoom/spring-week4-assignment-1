package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.junit.jupiter.api.BeforeEach;
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

    private static final Toy TOY
            = Toy.builder().name("쥐돌이").maker("어쩌구컴퍼니").price(BigDecimal.valueOf(3000)).build();

    @BeforeEach
    void setup() {
        final List<Toy> toys = List.of(TOY,
                Toy.builder().name("곰돌이").maker("어쩌구컴퍼니").price(BigDecimal.valueOf(3000)).build());

        given(repository.findAll()).willReturn(toys);
        given(repository.findById(eq(1L))).willReturn(Optional.of(TOY));
        given(repository.findById(eq(100L))).willReturn(Optional.empty());
    }

    @DisplayName("findAll은 모든 장난감을 반환한다.")
    @Test
    void findAllTest() {
        List<Toy> toys = service.findAll();

        verify(repository).findAll();
        assertThat(toys).isNotEmpty();
        assertThat(toys).hasSize(2);
    }

    @DisplayName("findById는 id에 해당하는 장난감을 반환한다.")
    @Test
    void findByIdTest() {
        Toy toy = service.findById(1L);
        assertThat(toy).isNotNull();
        assertThat(toy.getName()).isEqualTo(TOY.getName());
    }

    @DisplayName("findById는 존재하지 않는 id로 조회할 경우 예외를 던진다.")
    @Test
    void findByNotExistIdTest() {
        assertThatThrownBy(() -> service.findById(100L))
                .isInstanceOf(ToyNotFoundException.class);
    }

}
