package com.codesoom.domain;

import com.codesoom.exception.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 상품 데이터를 관리합니다.
 */

@Repository
public class ProductRepository {
    private final Map<Long, Product> products = new LinkedHashMap<>();
    private Long newId = 0L;

    /**
     * 상품 목록을 반환해준다.
     *
     * @return 상품 목록
     */
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    /**
     * id에 해당하는 상품을 반환해준다.
     *
     * @param id 상품 id
     * @return id에 해당하는 상품
     */
    public Product find(Long id) {
       Product product = findProduct(id);

        return product;
    }

    /**
     * 상품의 정보를 저장하고 반환한다.
     *
     * @param product 저장할 상품
     * @return 저장한 상품
     */
    public Product save(Product product) {
        Long id = generateId();
        product.setId(id);
        products.put(id, product);

        return product;
    }

    /**
     * 상품을 삭제한다.
     *
     * @param product 삭제할 상품
     */
    public void remove(Product product) {
        products.remove(product.getId());
    }

    /**
     * id에 해당하는 상품을 찾고 있다면 상품을 없다면 null을 반환한다.
     *
     * @param id 상품 아이디
     * @return id에 해당하는 상품
     */
    private Product findProduct(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("id가 null이므로 Product를 찾을 수 없습니다.");
        }
        return Optional.ofNullable(products.get(id)).orElseThrow(() -> new ProductNotFoundException("요청한 " + id + "의 Product를 찾지 못했습니다."));
    }

    /**
     * 아이디를 생성한다.
     *
     * @return 생성된 아이디
     */
    private synchronized Long generateId() {
        newId += 1;
        return newId;
    }
}
