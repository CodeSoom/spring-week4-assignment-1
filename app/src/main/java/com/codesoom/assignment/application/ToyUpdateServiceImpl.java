package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyDto;
import com.codesoom.assignment.domain.ToyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ToyUpdateServiceImpl implements ToyUpdateService{

    private final ToyRepository repository;

    @Override
    public Toy update(Long id, ToyDto toyDto) {
        Toy toy = repository.findById(id).orElseThrow(() -> new ToyNotFoundException(id));
        toy.update(toyDto);
        return toy;
    }

}
