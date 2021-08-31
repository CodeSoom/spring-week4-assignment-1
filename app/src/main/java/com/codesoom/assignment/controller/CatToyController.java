package com.codesoom.assignment.controller;


import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.service.CatToyCommandService;
import com.codesoom.assignment.service.CatToyQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 장난감에 대한 조회, 등록, 수정, 삭제 요청을 처리합니다.
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
public class CatToyController {

    private final CatToyQueryService catToyQueryService;

    private final CatToyCommandService catToyCommandService;

    public CatToyController(CatToyQueryService catToyQueryService, CatToyCommandService catToyCommandService) {
        this.catToyQueryService = catToyQueryService;
        this.catToyCommandService = catToyCommandService;
    }

    /**
     * 장난감 리스트를 조회합니다.
     *
     * @return 장난감 리스트
     */
    @GetMapping("")
    public List<CatToy> getCatToyList() {
        return catToyQueryService.getCatToyList();
    }

    /**
     * 장난감 상세 조회를 합니다.
     *
     * @param id 조회할 장난감 식별자
     * @return 조회한 장난감
     */
    @GetMapping("/{id}")
    public CatToy getCatToy(@PathVariable Long id) {
        return catToyQueryService.getCatToy(id);

    }

    /**
     * 장난감을 등록합니다.
     *
     * @param catToy 등록할 장난감
     * @return 등록된 장난감
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy createCatToy(@RequestBody CatToy catToy) {
        return catToyCommandService.createCatToy(catToy);
    }

    /**
     * 장난감 정보를 수정합니다.
     *
     * @param id 수정할 장난감 식별자
     * @param catToy 수정할 내용
     * @return 수정된 장난감
     */
    @PatchMapping("/{id}")
    public CatToy updateCatToy(@PathVariable Long id, @RequestBody CatToy catToy) {
        return catToyCommandService.updateCatToy(id, catToy);
    }

    /**
     * 장난감을 삭제합니다.
     *
     * @param id 삭제할 장난감 식별자
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatToyList(@PathVariable Long id) {
        catToyCommandService.deleteCatToy(id);
    }
}
