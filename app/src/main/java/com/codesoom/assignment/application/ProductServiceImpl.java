package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Product update(Long id, Product src) {
        final Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.update(src);

        return repository.save(product);
    }

    @Override
    public void delete(Long id) {
        final Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        repository.delete(product);
    }
}
