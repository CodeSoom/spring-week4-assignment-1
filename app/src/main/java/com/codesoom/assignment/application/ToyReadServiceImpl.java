package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ToyReadServiceImpl implements ToyReadService {

    private final ToyRepository repository;

    @Override
    public List<Toy> findAll() {
        return (List<Toy>) repository.findAll();
    }

    @Override
    public Toy findById(Long id) {
        Toy toy = repository.findById(id).orElseThrow(() -> new ToyNotFoundException(id));
        return toy;
    }

}
