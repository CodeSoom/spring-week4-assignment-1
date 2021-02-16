package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductApplicationService는")
public class ProductApplicationServiceTest {
    ProductApplicationService applicationService;
    List<Product> allProduct;

    @Given("product를 생성하지 않았을 떄")
    public void product를_생성하지_않았을_떄() {
        applicationService = new ProductApplicationService();
    }

    @When("모든 product를 가져오는 경우")
    public void 모든_product를_가져오는_경우() {
        allProduct = applicationService.getAllProducts();
    }

    @Then("빈 리스트가 반환된다")
    public void 빈_리스트가_반환된다() {
        assertThat(allProduct).hasSize(0);
    }
}
