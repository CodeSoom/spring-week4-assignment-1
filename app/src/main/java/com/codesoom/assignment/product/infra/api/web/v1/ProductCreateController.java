package com.codesoom.assignment.product.infra.api.web.v1;

import com.codesoom.assignment.product.application.ProductCreator;
import com.codesoom.assignment.product.domain.dto.ProductRequest;
import com.codesoom.assignment.product.domain.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProductCreateController {
    private final ProductCreator productCreator;

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        productRequest.validate();
        return new ProductResponse(productCreator.createProduct(productRequest));
    }
}
