package com.codesoom.assignment.product.infra.api.web.v1;

import com.codesoom.assignment.product.application.ProductCreator;
import com.codesoom.assignment.product.application.ProductDeleter;
import com.codesoom.assignment.product.application.ProductReader;
import com.codesoom.assignment.product.application.ProductUpdater;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.dto.ProductRequest;
import com.codesoom.assignment.product.domain.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductCreator productCreator;
    private final ProductReader productReader;
    private final ProductUpdater productUpdater;
    private final ProductDeleter productDeleter;

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return new ProductResponse(productCreator.createProduct(productRequest));
    }

    @GetMapping("/products")
    public List<ProductResponse> getProductList() {
        return ProductResponse.listOf(productReader.getProductList());
    }

    @GetMapping("/products/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        Product product = productReader.getProduct(id);
        return new ProductResponse(product);
    }

    @PatchMapping("/products/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product product = productUpdater.updateProduct(id, productRequest);
        return new ProductResponse(product);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productDeleter.deleteProduct(id);
    }
}
