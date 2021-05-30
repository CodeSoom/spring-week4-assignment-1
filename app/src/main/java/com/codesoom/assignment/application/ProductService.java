package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService implements ProductManagable {

    private ProductRepository productRepository;

    /**
     * 주어진 Repository 객체로 초기화하며 ProductService 객체를 생성합니다.
     *
     * @param productRepository 주어진 Repository 객체
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 모든 상품의 목록을 반환합니다.
     *
     * @return 상품 목록입니다.
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * id에 해당하는 상품을 반환합니다.
     *
     * @param id 상품의 id
     * @return id에 해당하는 상품
     * @exception EmptyResultDataAccessException
     *      id에 해당하는 상품이 없음
     */
    @Override
    public Product getProduct(Long id) {
        return findById(id);
    }

    /**
     * 상품을 저장합니다.
     *
     * @param product 주어진 상품
     * @return 저장된 상품
     */
    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * id에 해당하는 상품을 갱신합니다.
     *
     * @param id 상품의 id
     * @param sourceProduct 갱신할 내용
     * @return 갱신된 상품
     * @exception EmptyResultDataAccessException
     *      id에 해당하는 상품이 없음
     */
    @Override
    @Transactional
    public Product updateProduct(Long id, Product sourceProduct) {
        Product product = findById(id);
        product.updateBy(sourceProduct);
        return productRepository.save(product);
    }

    /**
     * id에 해당하는 상품을 삭제합니다.
     *
     * @param id 주어진 id
     * @exception EmptyResultDataAccessException
     *      id에 해당하는 상품이 없음
     */
    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(Math.toIntExact(id)));
    }
}
