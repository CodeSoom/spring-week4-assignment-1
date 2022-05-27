package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.interfaces.ProductDeleteService;
import com.codesoom.assignment.application.interfaces.ProductShowService;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.controllers.interfaces.ProductDeleteController;
import com.codesoom.assignment.controllers.interfaces.ProductDetailController;
import com.codesoom.assignment.domain.Toy;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ToyDeleteController implements ProductDeleteController {
    private final ProductDeleteService service;

    public ToyDeleteController(ProductDeleteService service) {
        this.service = service;
    }

    @DeleteMapping("{id}")
    @Override
    public void delete(Long id) {
        service.deleteBy(id);
    }
}
