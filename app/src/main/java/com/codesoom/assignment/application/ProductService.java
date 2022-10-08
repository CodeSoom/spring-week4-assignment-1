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
     * id로 product 객체를 찾아 리턴한다
     *
     * @param id the id
     * @return 찾은 상품
     */
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("해당 id를 가진 상품이 존재하지 않습니다 id:" + id));
    }

    /**
     * 모든 상품의 목록을 리턴합니다.
     *
     * @return 모든 상품
     */
    public List<Product> getList() {
        return productRepository.findAll();
    }

    /**
     * 상품을 삭제하고, 삭제한 정보를 리턴합니다
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
     * id로 product를 찾아
     * 매개변수로 받은 product객체의 정보로 업데이트 한 후 리턴한다.
     *
     * @param id
     * @param product
     * @return 업데이트 된 product 를 리턴한다
     */
    public Product update(Long id, Product product){
        return productRepository.findById(id)
            .map(this::detailUpdate)
            .orElseThrow(() -> new ProductNotFoundException("해당 id를 가진 상품이 존재하지 않습니다 id:" + id));
    }

    public Product detailUpdate(Product product) {
        if(product.getBrand() != null){
            product.updateBrand(product.getBrand());
        }
        if(product.getPrice() != null) {
            product.updatePrice(product.getPrice());
        }
        if(product.getImageUrl() != null) {
            product.updateImageUrl(product.getImageUrl());
        }
        return product;
    }

}
