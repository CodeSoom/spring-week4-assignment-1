package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.ProductSaveRequest;
import com.codesoom.assignment.domain.ProductUpdateRequest;
import com.codesoom.assignment.factories.ProductFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품 변경 작업
 */
@Transactional
@Service
public class ProductCommandService {

    private final ProductRepository productRepository;

    public ProductCommandService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 저장 후 반환
     */
    public Product saveProduct(final ProductSaveRequest productSaveRequest) {

        final Product product = productSaveRequest.toProduct();

        return productRepository.save(product);
    }

    /**
     * 상품 교체 후 반환
     *
     * @param product       교체 대상 상품
     * @param replaceSource 교체될 상품 데이터
     */
    public Product replaceProduct(final Product product, final ProductUpdateRequest replaceSource) {

        return ProductFactory.replaceProduct(product, replaceSource);
    }

    /**
     * 상품 변경 후 반환
     *
     * @param product      변경 대상 상품
     * @param updateSource 변경할 상품 데이터
     */
    public Product updateProduct(final Product product, final ProductUpdateRequest updateSource) {

        return ProductFactory.updateProduct(product, updateSource);
    }

    /**
     * 상품 삭제
     *
     * @param product 삭제할 상품
     */
    public void deleteProduct(final Product product) {
        productRepository.delete(product);
    }
}
