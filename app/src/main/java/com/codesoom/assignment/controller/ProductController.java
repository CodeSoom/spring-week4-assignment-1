package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.dto.ProductModel;
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
 * 고양이 장난감 생성, 조회, 수정, 삭제 API
 */
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * 고양이 장난감을 생성 후 리턴한다.
     * @param request
     * @return 고양이 장난감
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto.Response createCatToy(@RequestBody ProductDto.Request request) {
        ProductModel catToy = productService.createProduct(new ProductModel(request.getName(), request.getMaker(), request.getPrice(), request.getImageUrl()));
        return new ProductDto.Response(catToy);
    }

    /**
     * 고양이 장난감 리스트를 조회하여 리턴한다.
     * @return 고양이 장난감 리스트
     */
    @GetMapping
    public List<ProductDto.Response> selectCatToyList() {
        List<ProductModel> catToyList = productService.selectCatToyList();
        return ProductDto.Response.ofList(catToyList);
    }

    /**
     * 고양이 장난감 하나를 조회하여 리턴한다.
     * @param id
     * @return 고양이 장난감
     */
    @GetMapping(value = "/{id}")
    public ProductDto.Response selectCatToy(@PathVariable Long id) {
        ProductModel catToy = productService.selectProduct(id);
        return new ProductDto.Response(catToy);
    }

    /**
     * 고양이 장난감 하나를 수정한다.
     * @param id
     * @param request
     * @return 고양이 장난감
     */
    @PatchMapping(value = "/{id}")
    public ProductDto.Response modifyCatToy(@PathVariable Long id, @RequestBody ProductDto.Request request) {
        ProductModel catToy = productService.modifyProduct(
                new ProductModel(id, request.getName(), request.getMaker(), request.getPrice(), request.getImageUrl()));
        return new ProductDto.Response(catToy);
    }

    /**
     * 고양이 장난감을 삭제한다.
     * @param id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void modifyCatToy(@PathVariable Long id) {
        productService.deleteCatToy(id);
    }
}
