package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyService;
import com.codesoom.assignment.domain.ToyDto;
import com.codesoom.assignment.exceptions.NegativeIdException;
import com.codesoom.assignment.utils.ToyDtoValidator;

import java.util.List;

public class ToyController {

    private final ToyService service;

    public ToyController(ToyService service) {
        this.service = service;
    }

    public List<ToyDto> getToys() {
        return service.getToys();
    }

    public ToyDto getToy(Long id) {
        if (id < 0) {
            throw new NegativeIdException();
        }

        return service.getToy(id);
    }

    public ToyDto create(ToyDto dto) {
        ToyDtoValidator.validateDto(dto);

        return service.create(dto);
    }
}
