package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.error.NotFoundException;
import com.codesoom.assignment.interfaces.ProductRepository;
import com.codesoom.assignment.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyService implements ProductService {
    private final ProductRepository repository;

    public ToyService(ProductRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Product> findProducts() {
        return repository.findAll();
    }

    @Override
    public Product findProduct(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product source) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        product.setId(source.getId());
        product.setName(source.getName());
        product.setMaker(source.getMaker());
        product.setPrice(source.getPrice());
        product.setImageURI(source.getImageURI());

        return repository.update(product);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.delete(id);
    }
}
