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
     * 전체 장난감 리스트를 반환합니다.
     * @return 전체 Toy 리스트
     */
    @GetMapping
    public List<Toy> toyList() {
        return toyService.getAllToy();
    }

    /**
     * 요청한 id의 장난감을 리턴합니다.
     * @param id 요청 id
     * @return 상세 요청한 Toy
     */
    @GetMapping("{id}")
    public Toy detailToy(@PathVariable Long id) {
        return toyService.getToy(id);
    }

    /**
     * 장난감을 생성합니다.
     * @param toy toy 객체
     * @return 새로운 장난감
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Toy create(@RequestBody Toy toy) {
        return toyService.createToy(toy);
    }

    /**
     * 기존 장난감의 정보를 수정합니다.
     * @param id  해당 toy 자원 번호
     * @param toy toy 객체의 정보
     * @return 업데이트된 장난감
     */
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Toy patch(@PathVariable Long id, @RequestBody Toy toy) {
        return toyService.updateToy(id, toy);
    }

    /**
     * 요청한 id의 장난감을 삭제합니다.
     * @param id 해당 toy 자원 번호
     * @return 삭제된 장난감
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Toy delete(@PathVariable Long id) {
        return toyService.deleteToy(id);
    }
}
