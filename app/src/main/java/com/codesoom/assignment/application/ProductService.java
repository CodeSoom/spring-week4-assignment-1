// 1. getProducts -> 목록
// 2. getProduct -> 상세정보
// 3. createProduct -> 상품 추가
// 4. updateProduct -> 상품 수정
// 5. deleteProduct -> 상품 삭제

package com.codesoom.assignment.application;


import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private  final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return findProduct(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product source) {
        Product product = findProduct(id);

        product.change(source);

        return product;
    }

    public Product deleteProduct(long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException(id));
    }
}
