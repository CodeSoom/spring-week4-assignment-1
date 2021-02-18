package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 모든 상품 리스트를 반환합니다.
     *
     * @return 모든 상품들의 list
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * id에 해당하는 상품을 반환합니다.
     *
     * @param id 조회할 상품의 id
     * @return id에 해당하는 Product
     * @throws ProductNotFoundException id에 해당하는 상품이 없는 경우
     */
    public Product getProduct(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * 상품을 저장합니다.
     *
     * @param productDto 저장할 상품의 정보
     * @return 저장된 product
     */
    public Product addProduct(ProductDto productDto) {
        return productRepository.save(Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .maker(productDto.getMaker())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImageUrl())
                .build());
    }

    /**
     * id에 해당하는 장난감을 수정합니다.
     *
     * @param id         수정할 상품의 id
     * @param productDto 수정할 상품의 정보
     * @return 수정된 Product
     * @throws ProductNotFoundException id에 해당하는 상품이 없는 경우
     */
    public Product updateProduct(Long id, ProductDto productDto) throws ProductNotFoundException {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));
        product.update(productDto);
        return productRepository.save(product);
    }

    /**
     * id에 해당하는 상품을 수정합니다.
     *
     * @param id 삭제할 상품의 id
     * @throws ProductNotFoundException id에 해당하는 상품이 없는 경우
     */
    public void deleteProduct(Long id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}
