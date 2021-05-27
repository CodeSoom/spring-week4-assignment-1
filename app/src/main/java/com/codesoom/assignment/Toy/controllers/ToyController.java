package com.codesoom.assignment.Toy.controllers;

import com.codesoom.assignment.Toy.application.ToyService;
import com.codesoom.assignment.Toy.domain.Toy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Toy REST API 컨트롤러
 * 매핑 경로 - "/products"
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
public class ToyController {

    private ToyService toyService;

    /**
     * ToyController 생성자
     * @param toyService 생성자에 ToyService 의존성 주입(DI)
     */
    public ToyController(ToyService toyService) {
        this.toyService = toyService;
    }

    /**
     * Get Method with No resource
     * @return 전체 Toy 리스트
     */
    @GetMapping
    public List<Toy> toyList() {
        return toyService.getAllToy();
    }

    /**
     * 상세 Toy 정보
     * @param id 해당 toy 자원 번호
     * @return 상세 Toy 정보
     */
    @GetMapping("{id}")
    public Toy detailToy(@PathVariable Long id) {
        return toyService.getToyById(id);
    }

    /**
     * Toy 생성
     *
     * @param toy toy 객체
     * @return 생성된 Toy
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Toy create(@RequestBody Toy toy) {
        return toyService.createToy(toy);
    }

    /**
     * Toy 수정 - Patch
     * @param id  해당 toy 자원 번호
     * @param toy toy 객체의 정보
     * @return
     */
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Toy patch(@PathVariable Long id, @RequestBody Toy toy) {
        return toyService.updateToy(id, toy);
    }

    /**
     * Toy 삭제
     * @param id 해당 toy 자원 번호
     * @return 삭제된 toy 객체
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Toy delete(@PathVariable Long id) {
        return toyService.deleteToy(id);
    }
}
