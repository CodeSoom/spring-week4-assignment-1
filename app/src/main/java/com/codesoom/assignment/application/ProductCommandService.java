package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.ProductUpdateRequest;
import com.codesoom.assignment.dto.ProductSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품 정보의 변경을 책임집니다.
 */
@Transactional
@Service
public class ProductCommandService {

    private final ProductRepository productRepository;

    public ProductCommandService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품을 저장하고 리턴합니다.
     */
    public Product saveProduct(final ProductSaveRequest productSaveRequest) {

        final Product product = productSaveRequest.toProduct();

        return productRepository.save(product);
    }

    /**
     * 상품을 교체하고 리턴합니다.
     * @param product 교체 대상 상품
     * @param source 교체될 상품 데이터
     */
    public Product replaceProduct(final Product product, final ProductUpdateRequest source) {

        product.replace(source);

        return product;
    }

    /**
     * 상품을 변경하고 리턴합니다.
     * @param product 변경 대상 상품
     * @param source 변경할 상품 데이터
     */
    public Product updateProduct(final Product product, final ProductUpdateRequest source) {

        product.update(source);

        return product;
    }

    /**
     * 상품을 삭제 합니다.
     * @param product 삭제할 상품
     */
    public void deleteProduct(final Product product) {
        productRepository.delete(product);
    }
}
