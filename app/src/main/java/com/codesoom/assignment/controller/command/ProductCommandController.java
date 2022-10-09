package com.codesoom.assignment.controller.command;

import com.codesoom.assignment.application.command.ProductCommandService;
import com.codesoom.assignment.controller.ProductDto;
import com.codesoom.assignment.controller.ProductDtoMapper;
import com.codesoom.assignment.domain.ProductCommand.Register;
import com.codesoom.assignment.domain.ProductCommand.UpdateReq;
import com.codesoom.assignment.domain.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductCommandController {

    private final ProductCommandService productService;
    private final ProductDtoMapper productDtoMapper;

    public ProductCommandController(ProductCommandService productService, ProductDtoMapper productDtoMapper) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductInfo registerProduct(@RequestBody ProductDto.RequestParam request) {
        final Register command = productDtoMapper.of(request);
        return productService.createProduct(command);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfo updateProduct(@PathVariable Long id, @RequestBody ProductDto.RequestParam request) {
        final UpdateReq command = productDtoMapper.of(id, request);
        return productService.updateProduct(command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
