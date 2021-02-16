package com.codesoom.assignment.application;

import com.codesoom.assignment.adapter.InMemoryProductRepository;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductApplicationServiceTest {
    ProductRepository productRepository = new InMemoryProductRepository();
    ProductApplicationService applicationService = new ProductApplicationService(productRepository);

    List<Product> allProduct;
    List<Product> createdProductList = new ArrayList<>();

    @Given("product를 {int}개 생성했을 때")
    public void createProduct(int number) {
        String name = "고양이 인형";
        String maker = "라스 공방";
        String price = "1000원";
        String imageURL = "https://magical.dev/static/las.jpg";
        for (int i = 0; i < number; i++) {
            Product createdProduct = applicationService.createProduct(name, maker, price, imageURL);
            createdProductList.add(createdProduct);
        }
    }

    @When("모든 product를 가져오는 경우")
    public void getAllProducts() {
        allProduct = applicationService.getAllProducts();
    }

    @Then("{int}개의 product를 얻어올 수 있다")
    public void sameSizeAsCreatedProducts(int count) {
        assertThat(allProduct).hasSize(count);
    }

    @And("생성된 product들이 포함되어있다")
    public void containsCreatedProducts() {
        assertThat(allProduct).contains(createdProductList.toArray(allProduct.toArray(new Product[0])));
    }
}
