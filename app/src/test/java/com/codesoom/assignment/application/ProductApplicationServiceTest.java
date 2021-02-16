package com.codesoom.assignment.application;

import com.codesoom.assignment.adapter.InMemoryProductRepository;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductApplicationServiceTest {
    ProductRepository productRepository = new InMemoryProductRepository();
    ProductApplicationService applicationService = new ProductApplicationService(productRepository);

    List<Product> allProduct;
    Product createdProduct;

    @Given("product를 생성하지 않았을 떄")
    public void product를_생성하지_않았을_떄() {
        // pass
    }

    @When("모든 product를 가져오는 경우")
    public void 모든_product를_가져오는_경우() {
        allProduct = applicationService.getAllProducts();
    }

    @Then("빈 리스트가 반환된다")
    public void 빈_리스트가_반환된다() {
        assertThat(allProduct).hasSize(0);
    }

    @Given("product를 1개 생성했을 때")
    public void product를_1개_생성했을_때() {
        String name = "고양이 인형";
        String maker = "라스 공방";
        String price = "1000원";
        String imageURL = "https://magical.dev/static/las.jpg";

        createdProduct = applicationService.createProduct(name, maker, price, imageURL);
    }

    @Then("이미 생성된 1개의 product를 얻어올 수 있다")
    public void 이미_생성된_1개의_product를_얻어올_수_있다() {
        assertThat(allProduct).hasSize(1).contains(createdProduct);
    }
}
