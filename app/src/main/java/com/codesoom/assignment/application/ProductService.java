package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 상품(Product)과 관련된 비즈니스 로직을 담당합니다.
 *
 * @see Product
 * @see ProductRepository
 * @see com.codesoom.assignment.infra.InMemoryProductRepository
 * @see com.codesoom.assignment.infra.JpaProductRepository
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 모든 상품을 리턴합니다.
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 주어진 id에 해당하는 상품을 리턴합니다.
     *
     * @param id 찾는 상품에 해당하는 id
     * @return 주어진 id에 해당하는 상품
     * @throws ProductNotFoundException 주어진 id가 상품 목록에 없는 경우
     */
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * 새롭게 주어진 상품에 대한 정보를 저장하고, 그 상품을 리턴합니다.
     *
     * @param source 새롭게 만든 상품
     * @return 저장된 상품
     */
    public Product createProduct(Product source) {
        Product product = new Product(source.getName(), source.getMaker(), source.getPrice(), source.getImgUrl());

        return productRepository.save(product);
    }

    /**
     * 주어진 id에 해당하는 상품을 찾은 후 수정합니다.
     *
     * @param id 수정하려는 상품에 해당하는 id
     * @param source 수정하려는 상품
     * @return 수정된 상품
     * @throws ProductNotFoundException 주어진 id가 상품 목록에 없는 경우
     */
    public Product updateProduct(Long id, Product source) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(source.getName());
        product.setMaker(source.getMaker());
        product.setPrice(source.getPrice());
        product.setImgUrl(source.getImgUrl());

        return product;
    }

    /**
     * 주어진 id에 해당하는 상품을 찾아 삭제합니다.
     *
     * @param id 삭제하려는 상품에 해당하는 id
     * @throws ProductNotFoundException 주어진 id가 상품 목록에 없는 경우
     */
    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);

        return product;
    }

}
