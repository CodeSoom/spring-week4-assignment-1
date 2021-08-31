package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 고양이 장난감에 대한 Http Request 요청을 처리합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class CatToyController {

    private final CatToyService catToyService;


    /**
     * 고양이 장남감 전체 목록을 조회한다.
     * @return 장난감이 존재하지 않을 땐 빈 목록을 반환한다.
     */
    @GetMapping
    public List<CatToy> findAll() {
        return catToyService.findAll();
    }

    /**
     * 식별자를 이용해 고양이 장난감 상세정보를 조회한다.
     * @param id 고양이 장난감 식별자
     * @return 고양이 장난감 상세 정보
     */
    @GetMapping("/{id}")
    public CatToy findById(@PathVariable Long id) {
        return catToyService.findById(id);
    }

    /**
     * 새로운 고양이 장난감 정보를 생성한다.
     * @param catToy 고양이 장난감 정보
     * @return 생성된 고양이 장난감 상세 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy create(@RequestBody CatToy catToy) {
        return catToyService.save(catToy);
    }

    /**
     * 식별자를 이용해 조회한 장난감 정보를 수정한다.
     * @param id 고양이 장난감 식별자
     * @param catToy 수정할 장난감 정보
     * @return 수정된 고양이 장난감 상세 정보ㅗ
     */
    @PatchMapping("/{id}")
    public CatToy updateCatToy(@PathVariable Long id, @RequestBody CatToy catToy) {
        return catToyService.updateCatToy(id, catToy);
    }

    /**
     * 식별자를 이용해 조회한 장난감 정보를 삭제한다.
     * @param id 삭제할 고양이 장난감 식별자
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatToy(@PathVariable Long id) {
        catToyService.deleteToy(id);
    }



}
