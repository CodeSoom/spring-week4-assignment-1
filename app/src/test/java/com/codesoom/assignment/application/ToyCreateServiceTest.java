package com.codesoom.assignment.application;


import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ToyCreateServiceTest {

    @InjectMocks
    private ToyCreateServiceImpl service;

    @Mock
    private ToyRepository repository;

    @DisplayName("장난감을 성공적으로 등록한다.")
    @Test
    void createToyTest() {
        final ToyDto toyDto = new ToyDto(name, maker, price, image);

        service.create(toyDto);

        verify(repository.save(any(Toy.class)));
    }

}
