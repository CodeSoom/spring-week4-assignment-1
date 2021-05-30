package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.dto.ToySaveRequest;
import com.codesoom.assignment.service.ToyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 장난감에 대한 요청을 처리합니다.
 */
@RestController
@RequestMapping("/products")
public class ToyController {

    private ToyService toyService;

    public ToyController(ToyService toyService) {
        this.toyService = toyService;
    }

    /**
     * 장난감 목록을 반환합니다.
     *
     * @return 장난감 목록을 포함한 HttpResponse
     */
    @GetMapping
    public ResponseEntity<List<Toy>> getList() {
        List<Toy> toys = this.toyService.list();
        return new ResponseEntity<>(toys, HttpStatus.OK);
    }

    /**
     * 장난감 정보를 생성하고 반환합니다.
     *
     * @param toySaveRequest 생성될 장난감의 정보
     * @return 생성된 장난감의 정보를 포함한 HttpResponse
     */
    @PostMapping
    public ResponseEntity<Toy> createToy(
           @RequestBody ToySaveRequest toySaveRequest
    ) {
        Toy toy = this.toyService.create(toySaveRequest.toEntity());
        return new ResponseEntity<>(toy, HttpStatus.CREATED);
    }

    /**
     * 장난감 정보를 수정하고 반환합니다.
     *
     * @param id 수정할 장난감의 id
     * @param toySaveRequest 수정할 장난감의 정보
     * @return 수정된 장난감의 정보
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Toy> updateToy(
            @PathVariable Long id,
            @RequestBody ToySaveRequest toySaveRequest
    ) {
        Toy toy = this.toyService.update(toySaveRequest.toEntityWithId(id));
        return new ResponseEntity<>(toy, HttpStatus.OK);
    }
}
