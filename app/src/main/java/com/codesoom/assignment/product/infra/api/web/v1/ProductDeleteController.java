package com.codesoom.assignment.product.infra.api.web.v1;

import com.codesoom.assignment.product.application.ProductDeleter;
import com.codesoom.assignment.product.application.ProductReader;
import com.codesoom.assignment.product.application.ProductUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProductDeleteController {

    private final ProductDeleter productDeleter;

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productDeleter.deleteProduct(id);
    }
}
