package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductCommandRequest;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createTask(ProductCommandRequest productCommandRequest) {
        Product product = productCommandRequest.toEntity();
        Product newProduct = productRepository.save(product);
        return ProductResponse.of(newProduct);
    }

    /**
     *  id로 product를 조회해 productCommandRequest의 spec에 맞게 변경 후 변경한
     *  product를 ProductResponse로 변환해 반환합니다.
     *
     * @param id 변경할 product id
     * @param productCommandRequest 변경사항을 담은 dto
     * @throws ProductNotFoundException id로 조회한 product가 없을 때 던지는 예외
     */
    public ProductResponse updateProduct(Long id, ProductCommandRequest productCommandRequest) {
        Product product = findByIdOrElseThrow(id);
        product.update(
                productCommandRequest.getName(),
                productCommandRequest.getMaker(),
                productCommandRequest.getPrice(),
                productCommandRequest.getImageUrl());
        return ProductResponse.of(product);
    }

    /**
     * id로 product를 조회해 조회한 product를 ProductResponse를 변환해 리턴합니다.
     *
     * @param id 조회할 product id
     * @throws ProductNotFoundException id로 조회한 product가 없을 때 던지는 예외
     */
    public ProductResponse getProduct(Long id) {
        Product product = findByIdOrElseThrow(id);
        return ProductResponse.of(product);
    }


    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * id로 product를 조회해 해당 product를 삭제합니다.
     *
     * @param id 삭제할 product id
     * @throws ProductNotFoundException id로 조회한 product가 없을 때 던지는 예외
     */
    public void deleteProduct(Long id) {
        Product product = findByIdOrElseThrow(id);
        productRepository.delete(product);
    }

    private Product findByIdOrElseThrow(Long id) {
        return productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));
    }
}
