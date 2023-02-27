package com.codesoom.assignment.service;

import com.codesoom.assignment.common.exception.NotFoundMakerException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.dto.ListDto.ProductListDto;
import com.codesoom.assignment.dto.ProductSaveRequestDto;
import com.codesoom.assignment.dto.RequstMakerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductListDto> getProducts() {
        return this.productRepository.findAll().stream()
                .map(ProductListDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long registerProduct(ProductSaveRequestDto dto){
        return this.productRepository.save(dto.toEntity()).getId();
    }

    public List<Product> getProductByMaker(RequstMakerDto dto){
        validationByMaker(dto.toEntity().getMaker());
        return this.productRepository.findByMaker(dto.toEntity().getMaker());
    }

    @Transactional
    public void deleteProduct(RequstMakerDto dto){
        validationByMaker(dto.toEntity().getMaker());
         productRepository.deleteByMaker(dto.toEntity().getMaker());
    }

    private void validationByMaker(String maker) {
        List<Product> byMaker = productRepository.findByMaker(maker);
        if(byMaker.isEmpty()){
            throw new NotFoundMakerException(maker);
        }
    }

}
