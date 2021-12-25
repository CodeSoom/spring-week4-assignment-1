package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Toy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ToyServiceTest {
    private ToyService toyService;
    private static final String TOY_NAME = "test 장난감";
    private static final String TOY_MAKER = "애옹이네 장난감 가게";
    private static final Integer TOY_PRICE = 5000;
    private static final String TOY_IMAGE = "someUrl";

    @BeforeEach
    void setUp() {
        toyService = new ToyService();

        Toy toy = new Toy();
        toy.setName(TOY_NAME);
        toy.setMaker(TOY_MAKER);
        toy.setPrice(TOY_PRICE);
        toy.setImage(TOY_IMAGE);

        toyService.createProduct(toy);
    }

    @Test
    void getProducts() {
        List<Toy> toys = toyService.getProducts();
        assertThat(toys).hasSize(1);

        Toy toy = toys.get(0);
        assertThat(toy).isNotNull();
    }

    @Test
    void getProductWithExistedId() {
        Toy toy = toyService.getProduct(1L);

        assertThat(toy.getName()).isEqualTo(TOY_NAME);
    }

    @Test
    void getProductWithNotExistedId() {
        assertThatThrownBy(() -> toyService.getProduct(100L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void deleteProduct() {
        int oldSize = toyService.getProducts().size();

        toyService.deleteProduct(1L);

        int newSize = toyService.getProducts().size();

        assertThat(newSize - oldSize).isEqualTo(-1);
    }

    @Test
    void updateProductWithExistedId() {
        Toy source = new Toy();
        source.setName("Update" + TOY_NAME);

        Toy updatedToy = toyService.updateProduct(1L, source);

        assertThat(updatedToy.getName()).isEqualTo("Update" + TOY_NAME);
    }
}