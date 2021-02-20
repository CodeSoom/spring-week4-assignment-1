package com.codesoom.assignment.services;

import com.codesoom.assignment.models.Toy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CatToyService implements ToyService {
    @Override
    public List<Toy> find() {
        return null;
    }

    @Override
    public Toy find(Long id) {
        return null;
    }

    @Override
    public void insert(Toy toy) {

    }

    @Override
    public void modify(Long id, Toy toy) {

    }

    @Override
    public void delete(Long id) {

    }
}
