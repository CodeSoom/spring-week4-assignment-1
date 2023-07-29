package com.codesoom.assignment.product.infra.api.web.v1;

import com.codesoom.assignment.product.application.ProductUpdater;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.dto.ProductRequest;
import com.codesoom.assignment.product.domain.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProductUpdateController {
    private final ProductUpdater productUpdater;

    @PatchMapping("/products/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product product = productUpdater.updateProduct(id, productRequest);
        return new ProductResponse(product);
    }
}
