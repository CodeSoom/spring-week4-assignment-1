package com.codesoom.assignment.service;

import com.codesoom.assignment.common.exception.NotFoundIdException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.dto.ProductSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    //findAll -> 전체 제품 조회
    public List<ProductDto> getProducts() {
        return this.productRepository.findAll().stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    /**
     * save -> 저장
     *
     * @param productSaveRequestDto
     */
    @Transactional
    public Long registerProduct(ProductSaveRequestDto productSaveRequestDto) {
        return this.productRepository.save(productSaveRequestDto.toEntity()).getId();
    }

    /**
     * getProductById -> id로 조회
     *
     * @param id
     */
    public ProductDto getProductById(Long id) {
        validationByMaker(id);
        return this.productRepository.findById(id).stream().map(ProductDto::new)
                .findAny()
                .orElseThrow(() -> new NotFoundIdException(id));
    }

    @Transactional
    public void modifyProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundIdException(id));
        product.modifyProduct(productDto.getName(), productDto.getMaker(), productDto.getPrice(), productDto.getImg());
    }


    /**
     * deleteById -> 삭제
     *
     * @param id
     */
    @Transactional
    public void deleteProduct(Long id) {
        validationByMaker(id);
        productRepository.deleteById(id);
    }


    private void validationByMaker(Long Id) {
        Optional<Product> byMaker = productRepository.findById(Id);
        if (byMaker.isEmpty()) {
            throw new NotFoundIdException(Id);
        }
    }

}
