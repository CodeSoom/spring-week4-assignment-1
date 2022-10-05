package com.codesoom.assignment.application;

import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductCommand;
import com.codesoom.assignment.domain.ProductInfo;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 전체 상품 목록을 리턴한다
     * @return 전체 상품 목록
     */
    @Override
    public List<ProductInfo> getProducts() {
        List<ProductInfo> result = new ArrayList<>();

        productRepository.findAll().forEach(product -> result.add(new ProductInfo(product)));

        return result;
    }

    /**
     * 상품ID에 해당하는 상품을 리턴한다.
     * @param id 상품 ID
     * @return 검색된 상품
     * @throws ProductNotFoundException 상품이 없을 경우 발생하는 예외
     */
    @Override
    public ProductInfo getProduct(Long id) {
        return new ProductInfo(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)));
    }

    /**
     * 새로운 상품을 추가하고 추가된 상품을 리턴한다.
     * @param command 새로운 상품정보
     * @return 추가된 상품
     */
    @Transactional
    @Override
    public ProductInfo createProduct(ProductCommand.Register command) {
        return new ProductInfo(productRepository.save(command.toEntity()));
    }

    /**
     * 상품을 수정하고 수정된 상품을 리턴한다.
     * @param command 수정할 상품정보
     * @return 수정된 상품
     * @throws ProductNotFoundException 상품이 없을 경우 발생하는 예외
     */
    @Transactional
    @Override
    public ProductInfo updateProduct(ProductCommand.Register command) {
        Product product = command.toEntity();
        Product findProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException(product.getId()));

        return new ProductInfo(findProduct.modifyProduct(product));
    }

    /**
     * 상품ID에 해당하는 상품을 검색하고 해당 상품을 삭제한다.
     * @param id 상품 ID
     * @throws ProductNotFoundException 상품이 없을 경우 발생하는 예외
     */
    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(findProduct);
    }
}
