package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.ToyDto;

import java.util.List;

public interface ToyService {
    List<ToyDto> getToys();

    ToyDto getToy(Long id);

    ToyDto create(ToyDto any);
}
