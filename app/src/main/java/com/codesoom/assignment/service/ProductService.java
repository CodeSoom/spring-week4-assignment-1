package com.codesoom.assignment.service;

import com.codesoom.assignment.common.exception.NotFoundMakerException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.dto.ProductSaveRequestDto;
import com.codesoom.assignment.dto.RequstIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/*
 * 고양이 장난감 목록 얻기 - `GET /products` - 완료
 * 고양이 장난감 상세 조회하기 - `GET /products/{id}` - 완료
 * 고양이 장난감 등록하기 - `POST /products`
 * 고양이 장난감 수정하기 - `PATCH /products/{id}`
 * 고양이 장난감 삭제하기 - `DELETE /products/{id}`
 */


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
     * @param productSaveRequestDto
     */
    @Transactional
    public Long registerProduct(ProductSaveRequestDto productSaveRequestDto){
        return this.productRepository.save(productSaveRequestDto.toEntity()).getId();
    }

    /**findByMaker -> 메이커로 조회
     * @param RequstIdDto
     */
    public List<ProductDto> getProductById(RequstIdDto RequstIdDto){
        NullMaker(RequstIdDto.toEntity().getId());
        return this.productRepository.findById(RequstIdDto.toEntity().getId()).stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    /**
     * deleteById -> 삭제
     * @param requstIdDto
     */
    @Transactional
    public void deleteProduct(RequstIdDto requstIdDto){
        validationByMaker(requstIdDto.toEntity().getId());
         productRepository.deleteById(requstIdDto.toEntity().getId());
    }

    private void NullMaker(Long id) {
        Optional<Product> byMaker = productRepository.findById(id);
        if(byMaker.isEmpty()){
            throw new NotFoundMakerException(id);
        }
    }


    private void validationByMaker(Long Id) {
        Optional<Product> byMaker = productRepository.findById(Id);
        if(byMaker.isEmpty()){
            throw new NotFoundMakerException(Id);
        }
    }

}
