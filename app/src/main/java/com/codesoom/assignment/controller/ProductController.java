package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductCommandService;
import com.codesoom.assignment.application.ProductQueryService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductSaveDto;
import com.codesoom.assignment.dto.ProductUpdateDto;
import com.codesoom.assignment.dto.ProductViewDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품에 대한 HTTP 요청 처리
 */
@CrossOrigin(origins = "http://localhost:9000")
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
     * 상품 전체 목록 반환
     */
    @GetMapping
    public List<ProductViewDto> list() {

        List<Product> products = productQueryService.getProducts();

        return products.stream()
                .map(ProductViewDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 상품 생성 후 반환
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductViewDto save(@RequestBody ProductSaveDto productSaveDto) {

        final Product product = productCommandService.saveProduct(productSaveDto);

        return ProductViewDto.from(product);
    }

    /**
     * 상품 상세 정보 반환
     */
    @GetMapping("/{productId}")
    public ProductViewDto detail(@PathVariable Long productId) {

        final Product product = productQueryService.getProduct(productId);

        return ProductViewDto.from(product);
    }

    /**
     * 상품 삭제
     *
     * @param productId 삭제할 상품 아이디
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{productId}")
    public void delete(@PathVariable Long productId) {

        final Product product = productQueryService.getProduct(productId);

        productCommandService.deleteProduct(product);
    }

    /**
     * 상품을 새로운 상픔으로 대체하고 반환
     *
     * @param productId     - 대체될 상품 아이디
     * @param replaceSource - 대체할 데이터
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{productId}")
    public ProductViewDto replace(@PathVariable Long productId, @RequestBody ProductUpdateDto replaceSource) {

        final Product product = productQueryService.getProduct(productId);

        productCommandService.replaceProduct(product, replaceSource);

        return ProductViewDto.from(product);

    }

    /**
     * 상품 일부를 수정하고 반환
     *
     * @param productId    수정할 상품 아이디
     * @param updateSource 수정할 데이터
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{productId}")
    public ProductViewDto update(@PathVariable Long productId, @RequestBody ProductUpdateDto updateSource) {

        final Product product = productQueryService.getProduct(productId);

        productCommandService.updateProduct(product, updateSource);

        return ProductViewDto.from(product);
    }
}
