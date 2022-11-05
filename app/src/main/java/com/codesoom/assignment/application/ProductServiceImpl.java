package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Category;
import com.codesoom.assignment.domain.CategoryRepository;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exceptions.CategoryNotFoundException;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto::of)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ProductDto getProduct(Long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductDto.of(product);
    }

    @Override
    public ProductDto create(ProductDto dto) {
        final Product saved = productRepository.save(Product.of(dto, toCategories(dto.getCategoryNames())));

        return ProductDto.of(saved);
    }

    @Override
    public ProductDto update(Long id, ProductDto src) {
        final Product found = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        found.updateInfo(src, toCategories(src.getCategoryNames()));

        return ProductDto.of(found);
    }

    @Override
    public void delete(Long id) {
        final Product found = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(found);
    }

    private List<Category> toCategories(List<String> categoryNameList) {
        if (categoryNameList == null) {
            return null;
        }

        return categoryNameList
                .stream()
                .map(name -> categoryRepository.findByName(name)
                        .orElseThrow(() -> new CategoryNotFoundException(name)))
                .collect(Collectors.toList());
    }
}
