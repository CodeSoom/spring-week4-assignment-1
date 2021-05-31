package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.models.dto.ProductDto;
import com.codesoom.assignment.models.dto.ProductInfoDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 상품과 관련된 http 요청 처리를 담당합니다.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품 목록 조회 요청을 수신합니다.
     * @return 조회된 상품 목록을 반환합니다.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductInfoDto> list() {
        return productService.getProductList();
    }

    /**
     * 상품 상세조회 요청을 수신합니다.
     * @param productId 조회할 상품 ID
     * @return 조회된 상품 상세정보를 반환합니다.
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfoDto detail(@PathVariable("id") Long productId) {
        return productService.getProductOne(productId);
    }

    /**
     * 상품 등록 요청을 수신합니다.
     * @param saveParam 등록할 상품 내용
     * @return 등록된 상품을 반환합니다.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto save(@RequestBody ProductInfoDto saveParam) {
        return productService.saveProduct(saveParam);
    }

    /**
     * 상품 수정 요청을 수신합니다.
     * @param productId 수정할 상품 ID
     * @param updateParam 수정할 상품 내용
     * @return 수정된 상품정보를 반환합니다.
     */
    @RequestMapping(method={RequestMethod.PUT, RequestMethod.PATCH}, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfoDto update(@PathVariable("id") Long productId, @RequestBody ProductInfoDto updateParam) {
        return productService.updateProduct(productId, updateParam);
    }

    /**
     * 상품 삭제 요청을 수신합니다.
     * @param productId 삭제할 상품 ID
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
    }

}
