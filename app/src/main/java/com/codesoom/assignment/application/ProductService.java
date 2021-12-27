package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.errors.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 상품을 관리합니다.
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 서비스 객체를 생성합니다.
     * @param productRepository 상품 repository
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 목록을 반환합니다.
     * @return 상품 목록
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 주어진 id와 일치하는 상품을 반환합니다.
     * @param id 상품 id
     * @return 일치하는 id의 상품
     * @throws ProductNotFoundException 상품을 찾지 못한 경우
     */
    public Product getProduct(Long id) {
        String format = "아이디 [%d]에 해당하는 상품을 찾지 못했습니다.";

        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format(format, id)));
    }

    /**
     * 상품을 저장하고 저장된 상품을 반환합니다.
     * @param source 저장할 상품의 source
     * @return 저장된 상품
     */
    public Product saveProduct(Product source) {
        Product newProduct = Product.createSaveProduct(
                source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImageUrl()
        );

        return productRepository.save(newProduct);
    }

    /**
     * 주어진 id의 상품을 수정하고 수정된 상품을 반환합니다.
     * @param id 수정할 상품 id
     * @param source 수정할 상품 source
     * @return 수정된 상품
     * @throws ProductNotFoundException 상품을 찾지 못한 경우
     */
    public Product updateProduct(Long id, Product source) {
        String format = "아이디 [%d]에 해당하는 상품을 찾지 못했으므로, 상품을 수정하지 못했습니다.";

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format(format, id)));

        product.update(
                source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImageUrl()
        );

        return product;
    }

    /**
     * 주어진 id의 상품을 삭제합니다.
     * @param id 삭제할 상품 id
     * @throws ProductNotFoundException 상품을 찾지 못한 경우
     */
    public void deleteProduct(Long id) {
        String format = "아이디 [%d]에 해당하는 상품을 찾지 못했으므로 상품을 삭제하지 못했습니다.";

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format(format, id)));

        productRepository.delete(product);
    }
}
