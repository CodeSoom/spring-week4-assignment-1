package com.codesoom.assignment.product.infra.api.web.v1;

import com.codesoom.assignment.product.application.ProductReader;
import com.codesoom.assignment.product.application.ProductUpdater;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProductReadController {
    private final ProductReader productReader;


    @GetMapping("/products")
    public List<ProductResponse> getProductList() {
        return ProductResponse.listOf(productReader.getProductList());
    }

    @GetMapping("/products/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        Product product = productReader.getProduct(id);
        return new ProductResponse(product);
    }
}
