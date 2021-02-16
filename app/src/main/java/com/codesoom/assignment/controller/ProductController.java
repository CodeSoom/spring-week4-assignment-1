package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductRequest;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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
 * 상품과 관련된 HTTP 요청 처리를 담당합니다.
 *
 * @see ProductService
 * @see Product
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 모든 상품을 응답합니다.
     */
    @GetMapping
    public List<ProductResponse> list() {
        List<Product> products = productService.getProducts();

        return ProductResponse.listOf(products);
    }

    /**
     * 주어진 id에 해당하는 상품을 찾아 응답합니다.
     *
     * @param id 찾고자 하는 상품의 id
     * @return 찾은 상품
     */
    @GetMapping("{id}")
    public ProductResponse find(@PathVariable Long id) {
        Product foundProduct = productService.getProduct(id);

        return ProductResponse.of(foundProduct);
    }

    /**
     * 주어진 상품을 저장한 뒤, 저장된 상품을 응답합니다.
     *
     * @param productRequest 저장하고자 하는 상품
     * @return 저장된 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody ProductRequest productRequest) {
        Product product = productRequest.toProduct();
        Product createdProduct = productService.createProduct(product);

        return ProductResponse.of(createdProduct);
    }

    /**
     * 주어진 id에 해당하는 상품을 찾아 수정하고 수정된 상품을 응답합니다.
     *
     * @param id             수정하고자 하는 상품의 id
     * @param productRequest 수정하고자 하는 상품
     * @return 수정된 상품
     */
    @PatchMapping("{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product product = productRequest.toProduct();
        Product updatedProduct = productService.updateProduct(id, product);

        return ProductResponse.of(updatedProduct);
    }

    /**
     * 주어진 id에 해당하는 상품을 찾아 삭제합니다.
     *
     * @param id 삭제하고자 하는 상품의 id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
