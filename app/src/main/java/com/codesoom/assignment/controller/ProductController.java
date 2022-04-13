package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductCommandService;
import com.codesoom.assignment.application.ProductQueryService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductSaveDto;
import com.codesoom.assignment.dto.ProductViewDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품에 대한 HTTP 요청을 처리합니다.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductQueryService productQueryService;

    private final ProductCommandService productCommandService;

    public ProductController(ProductQueryService productQueryService, ProductCommandService productCommandService) {
        this.productQueryService = productQueryService;
        this.productCommandService = productCommandService;
    }

    /**
     * 상품 전체 목록을 리턴합니다.
     */
    @GetMapping
    public List<ProductViewDto> list() {

        List<Product> products = productQueryService.getProducts();

        return products.stream()
                .map(ProductViewDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 상품을 생성하고 리턴합니다.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductViewDto save(@RequestBody ProductSaveDto productSaveDto) {

        final Product product = productCommandService.saveProduct(productSaveDto);

        return ProductViewDto.from(product);
    }

    /**
     * 상품 상세 정보를 리턴합니다.
     */
    @GetMapping("/{productId}")
    public ProductViewDto detail(@PathVariable Long productId) {

        final Product product = productQueryService.getProduct(productId);

        return ProductViewDto.from(product);
    }

    /**
     * 상품을 삭제합니다.
     * @param productId 삭제할 상품 아이디
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{productId}")
    public void delete(@PathVariable Long productId) {

        final Product product = productQueryService.getProduct(productId);

        productCommandService.deleteProduct(product);
    }
}
