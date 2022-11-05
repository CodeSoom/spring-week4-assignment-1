package com.codesoom.assignment.product.application;

import com.codesoom.assignment.exception.products.ProductNotFoundException;
import com.codesoom.assignment.product.application.port.in.ProductCreateCommand;
import com.codesoom.assignment.product.application.port.in.ProductUpdateCommand;
import com.codesoom.assignment.product.application.port.in.ProductUseCase;
import com.codesoom.assignment.product.application.port.out.CommandProductPort;
import com.codesoom.assignment.product.application.port.out.QueryProductPort;
import com.codesoom.assignment.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Product createProduct(final ProductCreateCommand productCreateCommand) {
        return commandProductPort.save(productCreateCommand.toEntity());
    }

    @Override
    @Transactional
    public Product updateProduct(final Long id,
                                 final ProductUpdateCommand productUpdateCommand) {
        Product product = queryProductPort.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        product.update(productUpdateCommand.toEntity());

        return product;
    }

    @Override
    public void deleteProduct(final Long id) {
        Product product = queryProductPort.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        commandProductPort.delete(product);
    }
}
