package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.services.ToyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
* TODO
*  1. products() 전체 장난감 목록
*  2. product(id) 하나의 장난감
*  3. create(toy) 장난감 등록
*  4. delete(id) 등록된 장난감 삭제
* */
class ToyControllerTest {
    private ToyService toyService;
    private static final String TOY_NAME = "test 장난감";
    private static final String CREATE_PREFIX = "new";
    private static final String TOY_MAKER = "애옹이네 장난감 가게";
    private static final Integer TOY_PRICE = 5000;
    private static final String TOY_IMAGE = "someUrl";

    @BeforeEach
    void setUp() {
        toyService = new ToyService();

        Toy toy = new Toy();
        toy.setName(TOY_NAME);

        toyService.createProduct(toy);
    }

    @Test
    void products() {
        assertThat(toyService.getProducts()).isNotEmpty();
    }

    @Test
    void create() {
        Toy newToy = new Toy();

        newToy.setName(CREATE_PREFIX + TOY_NAME);
        newToy.setMaker(TOY_MAKER);
        newToy.setPrice(TOY_PRICE);
        newToy.setImage(TOY_IMAGE);

        Toy createdToy = toyService.createProduct(newToy);

        assertThat(createdToy.getName()).isEqualTo(CREATE_PREFIX + TOY_NAME);
        assertThat(createdToy.getMaker()).isEqualTo(TOY_MAKER);
        assertThat(createdToy.getPrice()).isEqualTo(TOY_PRICE);
        assertThat(createdToy.getImage()).isEqualTo(TOY_IMAGE);
    }

    @Test
    void product() {
        Toy find = toyService.getProduct(1L);

        assertThat(find.getName()).isEqualTo(TOY_NAME);
    }

    @Test
    void delete() {
        int oldSize = toyService.getProducts().size();

        toyService.deleteProduct(1L);

        int newSize = toyService.getProducts().size();

        assertThat(newSize - oldSize).isEqualTo(-1);
    }
}