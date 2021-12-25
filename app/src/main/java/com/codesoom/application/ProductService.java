package com.codesoom.application;

import com.codesoom.domain.Product;
import com.codesoom.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 상품을 관리합니다.
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 리스트를 반환합니다.
     *
     * @return 상품 리스트
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * id에 해당하는 상품을 반환합니다.
     *
     * @param id 상품 id
     * @return 해당하는 상품
     */
    public Optional<Product> getProduct(Long id) {
        return productRepository.find(id);
    }

    /**
     * 상품을 저장하고 반환합니다.
     *
     * @param product 저장할 상품
     * @return 저장된 상품
     */
    public Product createProduct(Product product) {

        return productRepository.save(product);
    }

    /**
     * id에 해당하는 상품을 수정하고 반환합니다.
     *
     * @param source 수정할 상품
     * @return 수정된 상품
     */
    public Optional<Product> updateProduct(Product source, Long id) {
        Optional<Product> product = productRepository.find(id);

        product.get().setName(source.getName());
        product.get().setPrice(source.getPrice());
        product.get().setMaker(source.getMaker());
        product.get().setImageUrl(source.getImageUrl());

        return product;
    }

    /**
     * id에 해당하는 상품을 삭제하고 남은 목록을 반환합니다.
     *
     * @param id 삭제할 상품id
     * @return 삭제 후 남은 상품 목록
     */
    public List<Product> deleteProduct(Long id) {
        Optional<Product> product = productRepository.find(id);

        productRepository.remove(product);
        return getProducts();
    }
}
