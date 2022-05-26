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
     * 첫번째 인자로 받은 id가 없을 경우 ProductNotFoundException 발생하고
     * id가 존재할 경우 두번 째 인자로 받은 productCommandRequest를 조회해 Product를 변경하는 메소드입니다.
     *
     * @param id 변경할 product id
     * @param productCommandRequest 변경사항을 담은 dto
     * @throws ProductNotFoundException id가 없을시 던지는 예외
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

    public ProductResponse getProduct(Long id) {
        Product product = findByIdOrElseThrow(id);
        return ProductResponse.of(product);
    }

    private Product findByIdOrElseThrow(Long id) {
        return productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
