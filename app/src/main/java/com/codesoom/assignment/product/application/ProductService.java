package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import com.codesoom.assignment.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 상품을 관리한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Transactional
    public Product updateProduct(long id, Product target) {
        final Product product = findById(id);
        product.update(target);

        return product;
    }

    public Product save(Product request) {
        final Product product = Product.from(request);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Product toy) {
        productRepository.delete(toy);
    }
}
