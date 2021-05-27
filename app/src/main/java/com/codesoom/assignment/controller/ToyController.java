package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.dto.ToySaveRequest;
import com.codesoom.assignment.service.ToyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    public ResponseEntity<Toy> createToy(
            @PathVariable @Valid ToySaveRequest toySaveRequest
    ) {
        Toy toy = this.toyService.create(toySaveRequest.toEntity());
        return new ResponseEntity<>(toy, HttpStatus.CREATED);
    }
}
