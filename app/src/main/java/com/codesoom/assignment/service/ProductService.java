package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ResourceNotFoundException;
import com.codesoom.assignment.repository.ProductJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductService{

    private final ProductJPARepository repository;

    public ProductService(ProductJPARepository repository) {
        this.repository = repository;
    }

    /**
     * 주어진 엔티티를 저장합니다
     *
     * @param   product
     * @return  저장된 product
     */
    public Product save(Product product){
        return repository.save(product);
    }

    /**
     * id에 해당하는 엔티티의 정보를 넘겨받은 엔티티로 대체한다
     * id는 변경되지 않는다
     * @param id 엔티티의 식별자
     * @param product 대체할 엔티티의 정보
     * @return 수정된 product
     */
    public Product update(Long id , Product product){
        Optional<Product> optionalProduct = repository.findById(id);
        optionalProduct
                .orElseThrow(() -> new ResourceNotFoundException("상품이 존재하지 않습니다. id : " + id));
        Product findProduct = optionalProduct.get();
        findProduct.setName(product.getName());
        findProduct.setMaker(product.getMaker());
        findProduct.setPrice(product.getPrice());
        findProduct.setFileName(product.getFileName());
        return findProduct;
    }

    /**
     * id에 해당하는 엔티티를 삭제한다
     * @param id 엔티티의 식별자
     */
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
