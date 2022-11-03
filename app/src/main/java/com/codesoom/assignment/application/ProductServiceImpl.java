package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Category;
import com.codesoom.assignment.domain.CategoryRepository;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.Categorization;
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

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll()
                .stream().map(ProductDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ProductDto getProduct(Long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductDto.from(product);
    }

    @Override
    public ProductDto create(ProductDto dto) {

        final List<Category> categoryList = dto.getCategoryNameList().stream()
                .map(categoryName ->
                        categoryRepository.findByName(categoryName)
                                .orElseThrow(() -> new CategoryNotFoundException(categoryName))
                ).collect(Collectors.toList());

        final Product product = dto.toProduct();
        mapProductWithCategory(product, categoryList);

        final Product savedProduct = productRepository.save(product);

        return ProductDto.from(savedProduct);
    }

    private void mapProductWithCategory(Product product, List<Category> categoryList) {
        for (Category category : categoryList) {
            final Categorization categorization = new Categorization(product, category);

            product.addProductCategory(categorization);
            category.addProductCategory(categorization);
        }
    }

    @Override
    public ProductDto update(Long id, ProductDto src) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.update(src);

        return ProductDto.from(product);
    }

    @Override
    public void delete(Long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }
}
