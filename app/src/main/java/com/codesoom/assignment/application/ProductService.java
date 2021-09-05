package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductModel;
import com.codesoom.assignment.exception.ExceptionMessageType;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 고양이 장난감 생성, 조회, 수정, 삭제한다.
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 고양이 장난감을 생성한다.
     * @param productModel
     * @return 고양이 장난감
     */
    public ProductModel createProduct(ProductModel productModel) {
        Product catToy = new Product(productModel.name(), productModel.maker(), productModel.price(), productModel.imageUrl());
        return new ProductModel(productRepository.save(catToy));
    }

    /**
     * 고양이 장난감 리스트를 조회하여 리턴한다.
     * @return 고양이 장난감 리스트
     */
    public List<ProductModel> selectCatToyList() {
        return ProductModel.ofList(productRepository.findAll());
    }

    /**
     * 고양이 장난감 하나를 조회하여 리턴한다.
     * @param id
     * @return 고양이 장난감
     */
    public ProductModel selectProduct(Long id) {
        return new ProductModel(findCatToy(id, ExceptionMessageType.GET));
    }

    /**
     * 고양이 장난감을 수정한다.
     * @param changeProductModel
     * @return 고양이 장난감
     */
    public ProductModel modifyProduct(ProductModel changeProductModel) {
        Product catToy = findCatToy(changeProductModel.id(), ExceptionMessageType.PUT);
        catToy.changeProduct(changeProductModel);
        return new ProductModel(productRepository.save(catToy));
    }

    /**
     * 고양이 장난감을 삭제한다.
     * @param catToyId
     */
    public void deleteCatToy(Long catToyId) {
        productRepository.delete(findCatToy(catToyId, ExceptionMessageType.DELETE));
    }

    /**
     * 고양이 장난감이 있으면 조회, 없으면 CatToyNotFoundException 발생
     * @param id
     * @param messageType
     * @throws ProductNotFoundException 고양이 장난감을 찾지 못한 경우
     * @return 고양이 장난감
     */
    private Product findCatToy(Long id, ExceptionMessageType messageType) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id, messageType));
    }
}
