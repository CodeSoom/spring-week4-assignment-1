package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 테스트 시에만 사용하기 위한 in memory repository
 */
@Repository
public class InMemoryProductRepository implements ProductRepository{

    private Long id = 0L;
    private List<Product> products = new ArrayList<Product>();

    private Long generateId() {
        this.id += 1L;
        return this.id;
    }

    private Product findOne(Long id) {
        // TODO 예외 처리 전략을 세운 후, Throw에 어떤 예외를 던질지 추가할 것.
        return products.stream()
                .filter(product -> product.checkMyId(id))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(Long id) {
        return findOne(id);
    }

    @Override
    public Product save(Product product) {
        if (product.isRegisted()) {
            Product updatedProject = products.stream().filter(source -> source.equals(product)).findFirst().orElseThrow();
            updatedProject.changeProduct(product);
            return updatedProject;
        }
        Product createdProduct = Product.creatNewProduct(generateId(), product);
        products.add(createdProduct);
        return createdProduct;
    }

    @Override
    public void delete(Long id) {
        Product deleteProduct = findOne(id);
        products.remove(deleteProduct);
    }
}
