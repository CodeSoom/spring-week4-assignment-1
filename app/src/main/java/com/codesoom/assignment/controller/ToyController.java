package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.dto.ToyResponse;
import com.codesoom.assignment.dto.ToySaveRequest;
import com.codesoom.assignment.service.ToyService;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public ResponseEntity<List<ToyResponse>> getList() {
        Iterable<Toy> toys = this.toyService.list();
        List<ToyResponse> response = new ArrayList<>();
        toys.forEach(toy -> response.add(toy.toResponse()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 요청한 장난감의 상세정보를 반환합니다.
     *
     * @param id 요청한 장난감의 id
     * @return 장난감의 상세정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<ToyResponse> getDetail(
            @PathVariable Long id
    ) {
        Toy toy = this.toyService.detail(id);
        return new ResponseEntity<>(toy.toResponse(), HttpStatus.OK);
    }

    /**
     * 장난감 정보를 생성하고 반환합니다.
     *
     * @param toySaveRequest 생성될 장난감의 정보
     * @return 생성된 장난감의 정보를 포함한 HttpResponse
     */
    @PostMapping
    public ResponseEntity<ToyResponse> createToy(
           @RequestBody ToySaveRequest toySaveRequest
    ) {
        Toy toy = this.toyService.create(toySaveRequest.toEntity());
        return new ResponseEntity<>(toy.toResponse(), HttpStatus.CREATED);
    }

    /**
     * 장난감 정보를 수정하고 반환합니다.
     *
     * @param id 수정할 장난감의 id
     * @param toySaveRequest 수정할 장난감의 정보
     * @return 수정된 장난감의 정보
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ToyResponse> updateToy(
            @PathVariable Long id,
            @RequestBody ToySaveRequest toySaveRequest
    ) {
        Toy toy = this.toyService.update(toySaveRequest.toEntityWithId(id));
        return new ResponseEntity<>(toy.toResponse(), HttpStatus.OK);
    }

    /**
     * 장난감 정보를 삭제합니다.
     *
     * @param id 삭제 요청된 장난감 정보의 id
     * @return 빈 컨텐츠
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteToy(
            @PathVariable Long id
    ) {
        this.toyService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
