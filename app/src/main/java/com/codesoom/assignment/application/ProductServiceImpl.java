package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Category;
import com.codesoom.assignment.domain.CategoryRepository;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductCategory;
import com.codesoom.assignment.domain.ProductCategoryRepository;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exceptions.CategoryNotFoundException;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductCategoryRepository productCategoryRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product create(Product product, String categoryName) {
        final Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName));

        mapProductToCategory(product, category);

        return productRepository.save(product);
    }

    private void mapProductToCategory(Product product, Category category) {
        final ProductCategory productCategory = new ProductCategory(product, category);

        productCategoryRepository.save(productCategory);

        product.addProductCategory(productCategory);
        category.addProductCategory(productCategory);
    }

    @Override
    public Product update(Long id, Product src) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.update(src);

        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }
}
