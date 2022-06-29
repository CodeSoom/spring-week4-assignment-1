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
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    /**
     * product 객체를 리포지토리에 생성한다
     * @param product 생성할 프러덕트
     * @return 생성한 프러덕트
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * 인자로 받은 id 값을 가진 프러덕트를 찾는다
     * @param productId 프러덕트 id
     * @return 찾은 프러덕트
     */
    public Product detailProduct(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    /**
     * 리포지토리에 등록된 모든 프러덕트를 반환한다
     * @return 등록된 프러덕트들의 리스트
     */
    public List<Product> listProduct() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, Product newProduct) {
        return productRepository.findById(id)
                .map(p -> p.update(newProduct))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
