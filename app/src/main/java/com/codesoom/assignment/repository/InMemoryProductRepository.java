package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 테스트 시에만 사용하기 위한 in memory repository
 */
@Primary
@Repository
public class InMemoryProductRepository implements ProductRepository{

    private Long id = 0L;
    private List<Product> products = new ArrayList<Product>();

    private Long generateId() {
        this.id += 1L;
        return this.id;
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.checkMyId(id))
                .findFirst();
    }

    @Override
    public Product save(Product product) {
        if (product.isRegistered()) {
            Product updatedProject = products.stream().filter(source -> source.equals(product)).findFirst().orElseThrow();
            updatedProject.changeProduct(product);
            return updatedProject;
        }
        Product createdProduct = Product.creatNewProduct(generateId(), product);
        products.add(createdProduct);
        return createdProduct;
    }

    @Override
    public void delete(Product product) {
        products.remove(product);
    }
}
