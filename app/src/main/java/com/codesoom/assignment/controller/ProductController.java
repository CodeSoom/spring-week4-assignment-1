package com.codesoom.assignment.controller;

import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.dto.ProductSaveRequestDto;
import com.codesoom.assignment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/*
 * 고양이 장난감 목록 얻기 - `GET /products` - 완료
 * 고양이 장난감 상세 조회하기 - `GET /products/{id}` - 완료
 * 고양이 장난감 등록하기 - `POST /products`
 * 고양이 장난감 수정하기 - `PATCH /products/{id}`
 * 고양이 장난감 삭제하기 - `DELETE /products/{id}`
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> Products() {
        return productService.getProducts();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto productDtoList(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCatToy(@RequestBody ProductSaveRequestDto request) {
        return productService.registerProduct(request);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id , @RequestBody ProductDto productDto){
       productService.modifyProduct(id , productDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
