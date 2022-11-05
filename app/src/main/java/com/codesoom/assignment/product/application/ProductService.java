package com.codesoom.assignment.product.application;

import com.codesoom.assignment.exception.products.ProductNotFoundException;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductCreateRequest;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductUpdateRequest;
import com.codesoom.assignment.product.application.port.in.ProductUseCase;
import com.codesoom.assignment.product.application.port.out.CommandProductPort;
import com.codesoom.assignment.product.application.port.out.QueryProductPort;
import com.codesoom.assignment.product.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductUseCase {
    private final QueryProductPort queryProductPort;
    private final CommandProductPort commandProductPort;

    public ProductService(QueryProductPort queryProductPort, CommandProductPort commandProductPort) {
        this.queryProductPort = queryProductPort;
        this.commandProductPort = commandProductPort;
    }

    @Override
    public List<Product> getProducts() {
        return queryProductPort.findAll();
    }

    @Override
    public Product getProduct(final Long id) {
        return queryProductPort.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public Product createProduct(final ProductCreateRequest productCreateRequest) {
        return commandProductPort.save(productCreateRequest.toEntity());
    }

    @Override
    public Product updateProduct(final Long id,
                                 final ProductUpdateRequest productUpdateRequest) {
        Product product = queryProductPort.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        product.update(productUpdateRequest.toEntity());

        return product;
    }

    @Override
    public void deleteProduct(final Long id) {
        Product product = queryProductPort.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        commandProductPort.delete(product);
    }
}
