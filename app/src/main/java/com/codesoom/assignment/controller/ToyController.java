package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.service.ToyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ToyController {

    private ToyService toyService;

    public ToyController(ToyService toyService) {
        this.toyService = toyService;
    }

    @GetMapping
    public ResponseEntity<List<Toy>> getList() {
        List<Toy> toys = this.toyService.list();
        return new ResponseEntity<>(toys, HttpStatus.OK);
    }
}
