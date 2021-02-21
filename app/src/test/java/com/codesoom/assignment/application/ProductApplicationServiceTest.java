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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductApplicationServiceTest {
    ProductRepository productRepository = new InMemoryProductRepository();
    ProductApplicationService applicationService = new ProductApplicationService(productRepository);

    List<Product> allProduct;
    List<Product> createdProductList = new ArrayList<>();
    Product createdProduct;

    String name;
    String maker;
    String price;
    String imageURL;

    @Given("product를 {int}개 생성했을 때")
    public void createProduct(int count) {
        provideProductInformation();

        for (int i = 0; i < count; i++) {
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

    @Given("product를 만드는데 필요한 데이터가 제공되었을 때")
    public void provideProductInformation() {
        name = "고양이 인형";
        maker = "라스 공방";
        price = "1000원";
        imageURL = "https://magical.dev/static/las.jpg";
    }

    @When("product를 생성하면")
    public void createProduct() {
        createdProduct = applicationService.createProduct(name, maker, price, imageURL);
    }

    @Then("생성된 product를 찾을 수 있다")
    public void findCreatedProduct() {
        assertThat(applicationService.getAllProducts()).contains(createdProduct);
    }

    @When("생성된 product를 삭제하면")
    public void deleteProduct() {
        applicationService.deleteProduct(createdProductList.get(0).productId().id());
    }

    @Then("생성된 product를 찾을 수 없다")
    public void canNotFountProduct() {
        assertThat(applicationService.getAllProducts()).doesNotContain(createdProduct);
    }

    @And("생성된 product만 가져올 수 있다")
    public void getSpecificProduct() {
        Optional<Product> product = applicationService.getProduct(createdProduct.productId().id());
        assertThat(product).isNotEmpty();
        assertThat(product.get()).isEqualTo(createdProduct);
    }
}