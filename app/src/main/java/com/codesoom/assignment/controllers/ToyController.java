package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.TaskService;
import com.codesoom.assignment.application.ToyService;
import com.codesoom.assignment.domain.Toy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ToyController {

    private ToyService toyService;

    public ToyController(ToyService toyService) {
        this.toyService = toyService;
    }

    @GetMapping
    public List<Toy> toyList() {
        return toyService.getAllToy();
    }

    @GetMapping("{id}")
    public Toy detailToy(@PathVariable Long id) {
        return toyService.getToyById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Toy create(@RequestBody Toy toy) {
        return toyService.createToy(toy);
    }
}
