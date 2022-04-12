package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyDto;
import com.codesoom.assignment.domain.ToyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ToyCreateServiceImpl implements ToyCreateService {

    private final ToyRepository repository;

    @Override
    public Toy create(ToyDto toyDto) {
        return repository.save(toyDto.toEntity());
    }

}
