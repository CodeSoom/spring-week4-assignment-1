package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.application.ToyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/*
* TODO
*  1. products() 전체 장난감 목록
*  2. product(id) 하나의 장난감
*  3. create(toy) 장난감 등록
*  4. delete(id) 등록된 장난감 삭제
* */
class ToyControllerTest {
    private ToyController toyController;
    private ToyService toyService;

    private static final String TOY_NAME = "test 장난감";
    private static final String UPDATE_PREFIX = "new";
    private static final String TOY_MAKER = "애옹이네 장난감 가게";
    private static final Integer TOY_PRICE = 5000;
    private static final String TOY_IMAGE = "someUrl";
    private static final Long NOT_EXISTED_ID = 100L;

    @BeforeEach
    void setUp() {
        toyService = mock(ToyService.class);

        Toy toy = new Toy();

        toy.setName(TOY_NAME);
        toy.setMaker(TOY_MAKER);
        toy.setPrice(TOY_PRICE);
        toy.setImage(TOY_IMAGE);

        List<Toy> toys = new ArrayList<>();
        toys.add(toy);

        given(toyService.getProducts()).willReturn(toys);
        given(toyService.getProduct(1L)).willReturn(toy);
        given(toyService.getProduct(NOT_EXISTED_ID)).willThrow(new ProductNotFoundException(NOT_EXISTED_ID));
        given(toyService.updateProduct(eq(NOT_EXISTED_ID), any(Toy.class)))
                .willThrow(new ProductNotFoundException(NOT_EXISTED_ID));
        given(toyService.deleteProduct(NOT_EXISTED_ID)).willThrow(new ProductNotFoundException(NOT_EXISTED_ID));

        toyController = new ToyController(toyService);
    }

    @Test
    void products() {
        assertThat(toyService.getProducts()).isNotEmpty();

        verify(toyService).getProducts();
    }

    @Test
    void productWithExistedId() {
        Toy toy = toyController.product(1L);

        assertThat(toy.getName()).isEqualTo(TOY_NAME);

        verify(toyService).getProduct(1L);
    }

    @Test
    void productWithNotExistedId() {
        assertThatThrownBy(() -> toyController.product(NOT_EXISTED_ID))
                .isInstanceOf(ProductNotFoundException.class);

        verify(toyService).getProduct(NOT_EXISTED_ID);
    }

    @Test
    void createNewProduct() {
        Toy newToy = new Toy();
        newToy.setName(TOY_NAME);

        toyController.create(newToy);

        verify(toyService).createProduct(newToy);
    }

    @Test
    void updateProductWithExistedId() {
        Toy source = new Toy();
        source.setName(UPDATE_PREFIX + TOY_NAME);

        toyController.update(1L, source);

        verify(toyService).updateProduct(1L, source);
    }

    @Test
    void updateProductWithNotExistedId() {
        Toy source = new Toy();
        source.setName(UPDATE_PREFIX + TOY_NAME);

        assertThatThrownBy(() -> toyController.update(NOT_EXISTED_ID, source))
                .isInstanceOf(ProductNotFoundException.class);

        verify(toyService).updateProduct(NOT_EXISTED_ID, source);
    }

    @Test
    void deleteProductWithExistedId() {
        toyController.delete(1L);

        verify(toyService).deleteProduct(1L);
    }

    @Test
    void deleteProductWithNotExistedId() {
        assertThatThrownBy(() -> toyController.delete(NOT_EXISTED_ID))
                .isInstanceOf(ProductNotFoundException.class);

        verify(toyService).deleteProduct(NOT_EXISTED_ID);
    }
}