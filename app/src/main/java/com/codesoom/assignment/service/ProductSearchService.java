package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ResourceNotFoundException;
import com.codesoom.assignment.repository.ProductJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ProductSearchService {

    private final ProductJPARepository repository;

    /**
     * 모든 엔티티를 반환합니다
     *
     * @return List<엔티티>
     */
    public Iterable<Product> findAll(){
        return repository.findAll();
    }

    /**
     * 식별자와 같은 엔티티를 찾아 반환합니다
     *
     * @param   id
     * @return  id에 해당하는 자원
     * @throws ResourceNotFoundException
     *          id에 해당하는 자원이 존재하지 않을 때 던져진다
     */
    public Product findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("상품이 존재하지 않습니다. id : " + id));
    }
}
