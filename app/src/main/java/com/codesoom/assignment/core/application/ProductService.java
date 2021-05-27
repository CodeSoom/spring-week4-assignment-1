package com.codesoom.assignment.core.application;

import com.codesoom.assignment.core.domain.Product;
import com.codesoom.assignment.core.domain.ProductRepository;
import com.codesoom.assignment.core.infra.JpaToyRepository;
import com.codesoom.assignment.web.exception.InvalidProductException;
import com.codesoom.assignment.web.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 고양이 장난감 데이터를 가공하여 반환하거나 처리합니다.
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(JpaToyRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 고양이 장난감 목록을 반환합니다.
     * @return 고양이 장난감 목록
     */
    public List<Product> fetchProducts() {
        return productRepository.findAll();
    }

    /**
     * 고양이 장난감을 등록합니다.
     * @param product
     * @return 저장된 장난감
     */
    public Product saveProduct(Product product) {
        if (StringUtils.isEmpty(product.getName()) ||
            StringUtils.isEmpty(product.getMaker()) ||
            StringUtils.isEmpty(product.getPrice())) {
            throw new InvalidProductException();
        }
        return productRepository.save(product);
    }

    /**
     * ID에 해당하는 장난감을 반환합니다.
     * @param id
     * @return 찾아진 장난감
     */
    public Product fetchProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException());
    }

    /**
     * ID에 해당하는 장난감을 삭제합니다.
     * @param id
     */
    public void deleteProductById(Long id) {
        Product product = fetchProductById(id);
        productRepository.delete(product);
    }

    /**
     * ID에 해당하는 장난감의 상세정보를 갱신합니다.
     * @param id
     * @return 갱신된 장난감
     */
    public Product updateProductById(Long id, Product product) {
        if(!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }

        return productRepository.save(product);
    }
}
