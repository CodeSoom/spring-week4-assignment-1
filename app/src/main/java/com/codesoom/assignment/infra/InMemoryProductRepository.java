package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = new ArrayList<>();
    private Long currentProductId = 0L;

    private Long generateId() {
        currentProductId += 1;
        return currentProductId;
    }

    /**
     * 상품 목록을 반환합니다.
     *
     * @return 상품 목록
     */
    @Override
    public List<Product> findAll() {
        return products;
    }

    /**
     * id에 해당하는 상품을 반환합니다.
     *
     * @param id 주어진 상품 id
     * @return id에 해당하는 상품
     * @exception EmptyResultDataAccessException
     *      id에 해당하는 상품이 없음
     */
    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EmptyResultDataAccessException(Math.toIntExact(id))));
    }

    /**
     * 상품을 추가합니다.
     *
     * @param product 추가할 상품
     * @return 추가한 상품
     */
    @Override
    public Product save(Product product) {
        product.setId(generateId());
        products.add(product);
        return product;
    }

    /**
     * id에 해당하는 상품을 제거합니다.
     *
     * @param id 주어진 id
     * @exception EmptyResultDataAccessException
     *      id에 해당하는 상품이 없음
     */
    @Override
    public void deleteById(Long id) {
        Product product = products.stream()
                                .filter(p -> p.getId().equals(id))
                                .findFirst()
                                .orElseThrow(() -> new EmptyResultDataAccessException(Math.toIntExact(id)));
        products.remove(product);
    }
}
