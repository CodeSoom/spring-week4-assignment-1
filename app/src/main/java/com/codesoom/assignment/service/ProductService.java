package com.codesoom.assignment.service;

import com.codesoom.assignment.common.exception.NotFoundIdException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.dto.ProductSaveRequestDto;
import com.codesoom.assignment.dto.RequstIdDto;
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
     * @param RequstIdDto
     */
    public List<ProductDto> getProductById(RequstIdDto RequstIdDto) {
        NullMaker(RequstIdDto.toEntity().getId());
        return this.productRepository.findById(RequstIdDto.toEntity().getId()).stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void modifyProduct(ProductDto productDto) {
        Product toy = productRepository.findById(productDto.getId()).orElseThrow(() -> new NotFoundIdException(productDto.getId()));
        toy.modifyProduct(productDto.getName(), productDto.getMaker(), productDto.getPrice(), productDto.getImg());
        productRepository.save(toy);
    }


    /**
     * deleteById -> 삭제
     *
     * @param requstIdDto
     */
    @Transactional
    public void deleteProduct(RequstIdDto requstIdDto) {
        validationByMaker(requstIdDto.toEntity().getId());
        productRepository.deleteById(requstIdDto.toEntity().getId());
    }

    private void NullMaker(Long id) {
        Optional<Product> byMaker = productRepository.findById(id);
        if (byMaker.isEmpty()) {
            throw new NotFoundIdException(id);
        }
    }


    private void validationByMaker(Long Id) {
        Optional<Product> byMaker = productRepository.findById(Id);
        if (byMaker.isEmpty()) {
            throw new NotFoundIdException(Id);
        }
    }

}
