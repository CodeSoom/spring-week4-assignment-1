package com.codesoom.assignment.application;

import com.codesoom.assignment.entity.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 새로운 상품을 생성한다.
     *
     * @param product the product
     * @return 저장한 상품
     */
    public Product create(Product product) {
       return productRepository.save(product);
    }

    /**
     * 상품을 찾아 리턴한다.
     *
     * @param id the id
     * @return 찾은 상품
     */
    public Product findById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("해당 id를 가진 상품이 존재하지 않습니다 id:" + id));
    }

    /**
     * 모든 상품의 목록을 리턴한다.
     *
     * @return 모든 상품
     */
    public List<Product> getList() {
        return productRepository.findAll();
    }

    /**
     * 상품을 삭제하고, 삭제한 정보를 리턴한다.
     *
     * @param id 삭제할 상품의 식별자
     * @return 삭제한 상품 정보
     */
    public Product remove(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("해당 id를 가진 상품이 존재하지 않습니다 id:" + id));
        productRepository.deleteById(product.getId());
        return product;
    }

    /**
     * id로 상품을 찾아
     * 매개변수로 받은 새로운 상품의 정보로 업데이트 한 후 리턴한다.
     *
     * @param id
     * @param update
     * @return 업데이트 된 상품을 리턴한다.
     */
    public Product update(Long id, Product update){
        Product foundProduct = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("해당 id를 가진 상품이 존재하지 않습니다 id:" + id));
        return detailUpdate(foundProduct, update);
    }

    public Product detailUpdate(Product source,Product update) {

        if(update.getBrand() != null){
            source.updateBrand(update.getBrand());
        }
        if(update.getPrice() != null) {
            source.updatePrice(update.getPrice());
        }
        if(update.getImageUrl() != null) {
            source.updateImageUrl(update.getImageUrl());
        }
        return source;
    }

}
