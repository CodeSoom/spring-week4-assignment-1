package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.interfaces.ProductCrudService;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.controllers.interfaces.ProductDetailController;
import com.codesoom.assignment.domain.Toy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class ToyDetailController implements ProductDetailController {
    private final ProductCrudService service;

    public ToyDetailController(ProductCrudService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @Override
    public ToyResponseDto detail(Long id) {
        Toy toy = service.showById(id);
        return new ToyResponseDto(toy);
    }
}
