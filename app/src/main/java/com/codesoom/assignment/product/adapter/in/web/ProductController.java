package com.codesoom.assignment.product.adapter.in.web;

import com.codesoom.assignment.product.adapter.in.web.dto.ProductCreateRequest;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductUpdateRequest;
import com.codesoom.assignment.product.application.port.in.ProductUseCase;
import com.codesoom.assignment.product.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    /**
     * 상품 목록을 리턴합니다.
     *
     * @return 상품 목록 리턴
     */
    @GetMapping
    public List<Product> list() {
        return productUseCase.getProducts();
    }

    /**
     * 상품의 상세 정보를 리턴합니다.
     *
     * @param id 상품 고유 id
     * @return 상품 상세 정보 리턴
     */
    @GetMapping("/{id}")
    public Product detail(@PathVariable final Long id) {
        return productUseCase.getProduct(id);
    }

    /**
     * 상품을 생성하여 리턴합니다.
     *
     * @param productCreateRequest 생성할 상품 정보
     * @return 생성한 상품 정보 리턴
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody final ProductCreateRequest productCreateRequest) {
        return productUseCase.createProduct(productCreateRequest.toCommand());
    }

    /**
     * 상품을 수정하여 리턴합니다.
     *
     * @param id                   상품 고유 id
     * @param productUpdateRequest 수정할 상품 정보
     * @return 수정한 상품 정보 리턴
     */
    @RequestMapping(path = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Product update(@PathVariable final Long id,
                          @RequestBody final ProductUpdateRequest productUpdateRequest) {
        return productUseCase.updateProduct(id, productUpdateRequest.toCommand());
    }

    /**
     * 상품을 삭제합니다.
     *
     * @param id 상품 고유 id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        productUseCase.deleteProduct(id);
    }
}
