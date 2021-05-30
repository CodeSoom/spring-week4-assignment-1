package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 고양이 장난감의 리스트를 반환한다.
     * @param
     */
    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }
    /**
     * 요청한 고양이 장난감을 반환한다.
     * @param id 고양이 장난감 아이디
     */
    @GetMapping("/{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 고양이 장난감을 등록한다.
     * @param source 생성할 고양이 장난감을 요청한다.
     * @return Product 생성한 고양이 장난감 을 반환한다.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product source) {
        return productService.saveProduct(source);
    }

    /**
     * 고양이 장난감을 수정한다.
     * @param id 고양이 장난감 아이디를 요청한다.
     * @param source 고양이 장난감 수정 데이터를 요청한다.
     * @return 수정된 고양이 장난감을 반환.
     */
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product source) {
        return productService.updateProduct(id, source);

    }
    /**
     * 고양이 장난감을 수정한다.
     * @param id 수정할 고양이 장난감 아이디를 요청한다.
     * @param source 고양이 장난감 수정 데이터를 요청한다.
     * @return 수정된 고양이 장난감을 반환.
     */
    @PatchMapping("/{id}")
    public Product patchProduct(@PathVariable Long id, @RequestBody Product source) {
        return productService.updateProduct(id, source);
    }


    /**
     * 고양이 장난감을 삭제한다.
     * @param id 삭제할 고양이 장난감 아이디를 요청한다.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
