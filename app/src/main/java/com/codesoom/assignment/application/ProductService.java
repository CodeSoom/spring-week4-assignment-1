package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.request.ProductRequest;
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
     *
     * @return 모든 상품 목록을 리턴합니다.
     */
    public List<Product> findAll() {
        return productRepository.findAll();

    }

    /**
     * Client가 요청한 id에 해당하는 상품을 찾아 리턴합니다.
     *
     * @param id Client가 검색하고자 하는 상품 id
     * @return 요청한 id에 해당하는 상품 값 반환
     * @throws ProductNotFoundException 요청한 id에 해당하는 상품을 찾지 못했을 경우 예외를 준다.
     */
    public Product find(Long id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Client가 입력한 상품 값을 저장하고, 저장된 상품값을 리턴합니다.
     * 
     * @param request client가 입력한 상품 값
     * @return 저장된 상품 값
     */
    public Product create(ProductRequest request) {
        return productRepository.save(request.toEntity());
    }

    public Product update(Long id, Product source) {
        Product product = find(id);
        product.update(source);

        return null;
    }
}
