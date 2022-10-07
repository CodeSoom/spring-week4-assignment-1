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
     * 매개변수로 받은 product 객체를 db에 저장한다
     *
     * @param product the product
     * @return 저장한 product 를 리턴한다
     */
    public Product create(Product product) {
       return productRepository.save(product);
    }

    /**
     * id로 product 객체를 찾아 리턴한다
     *
     * @param id the id
     * @return id로 찾은 product객체를 리턴한다
     */
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("해당 id를 가진 상품이 존재하지 않습니다 id:" + id));
    }

    /**
     *
     * @return productList를 리턴한다
     */
    public List<Product> getList() {
        return productRepository.findAll();
    }

    /**
     * id로 찾은 product 를 삭제한다
     *
     * @param id the id
     * @return the product
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
            .map(i->i.updateProduct(product))
            .orElseThrow(() -> new ProductNotFoundException("해당 id를 가진 상품이 존재하지 않습니다 id:" + id));
    }

}
