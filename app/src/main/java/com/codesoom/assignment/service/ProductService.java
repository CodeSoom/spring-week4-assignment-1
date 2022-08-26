package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ResourceNotFoundException;
import com.codesoom.assignment.repository.ProductJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService{

    private final ProductJPARepository repository;

    public ProductService(ProductJPARepository repository) {
        this.repository = repository;
    }

    /**
     * 모든 엔티티를 반환합니다
     *
     * @return List<엔티티>
     */
    @Transactional(readOnly = true)
    public List<Product> findAll(){
        return (List<Product>) repository.findAll();
    }

    /**
     * 식별자와 같은 엔티티를 찾아 반환합니다
     *
     * @param   id
     * @return  id에 해당하는 자원
     * @throws  ResourceNotFoundException
     *          id에 해당하는 자원이 존재하지 않을 때 던져진다
     */
    @Transactional
    public Product findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("상품이 존재하지 않습니다. id : " + id));
    }

    /**
     * 주어진 엔티티를 저장합니다
     *
     * @param   product
     * @return  저장된 product
     */
    @Transactional
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
    @Transactional
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
    @Transactional
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
