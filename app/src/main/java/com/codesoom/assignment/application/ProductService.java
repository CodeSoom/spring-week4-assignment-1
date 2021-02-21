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
     * 모든 상품 목록을 리턴합니다.
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 요청한 id에 해당하는 상품을 찾아 리턴합니다.
     *
     * @param id 검색하고자 하는 상품 id
     * @return 요청한 id에 해당하는 상품 값 반환
     * @throws ProductNotFoundException 요청한 id에 해당하는 상품을 찾지 못했을 경우
     */
    public Product getProduct(Long id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * 입력한 상품 값을 저장하고, 저장된 상품값을 리턴합니다.
     * 
     * @param request 입력한 상품 값
     * @return 저장된 상품 값
     */
    public Product createProduct(ProductRequest request) {
        return productRepository.save(request.toEntity());
    }

    /**
     * 요청한 id에 해당하는 상품을 찾아 업데이트하고 리턴합니다.
     *
     * @param id 검색하고자 하는 상품 id
     * @param request 변경하고자 하는 상품 값
     * @return 변경된 상품
     */
    public Product updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.update(request.toEntity());

        return productRepository.save(product);
    }

    /**
     * 요청한 id에 해당하는 상품을 찾아 삭제 합니다.
     *
     * @param id 삭제하고자 하는 상품 id
     */
    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);

        return product;
    }
}
