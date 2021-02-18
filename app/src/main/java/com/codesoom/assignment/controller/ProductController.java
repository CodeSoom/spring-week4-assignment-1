package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.dto.ProductRequestDto;
import com.codesoom.assignment.dto.ProductResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 상품 컨트롤러
 * @Author wenodev
 */
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 전체 상품 조회
     * @return 상품 정보 리스트
     */
    @GetMapping
    public List<ProductResponseDto> getProducts(){
        return productService.getProducts();
    }

    /**
     * 상품 저장
     * @param request 등록할 상품 정보
     * @return 등혹한 상품 정보
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponseDto saveProduct(@RequestBody ProductRequestDto request){
        return productService.saveProduct(request);
    }

    /**
     * 상품 1개 조회
     * @param id 상품 id
     * @return id 값으로 찾은 상품
     */
    @GetMapping("/{id}")
    public ProductResponseDto getProductsById(@PathVariable Long id){
        return productService.getProductsById(id);
    }

    /**
     * 상품 수정
     * @param id 상품 id
     * @param request 수정하고 싶은 상품 정보
     * @return
     */
    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto request){
        return productService.updateProduct(id, request);
    }

}
