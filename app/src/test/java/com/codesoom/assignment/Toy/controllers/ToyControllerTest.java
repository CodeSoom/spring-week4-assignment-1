package com.codesoom.assignment.Toy.controllers;

import com.codesoom.assignment.Toy.application.ToyService;
import com.codesoom.assignment.Toy.domain.Toy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@DisplayName("ToyController class")
class ToyControllerTest {
    private ToyController controller;
    private ToyService toyService;

    String TOY_NAME = "CAT TOWER";
    Long TOY_PRICE = 10000L;
    String TOY_IMAGE_URL = "https://www.google.com/imgres?imgurl=https%3A%2F%2Fcdn.hellodd.com%2Fnews%2Fphoto%2F202005%2F71835_craw1.jpg&imgrefurl=https%3A%2F%2Fwww.hellodd.com%2Fnews%2FarticleView.html%3Fidxno%3D71835&tbnid=suDfwSE37I-hmM&vet=12ahUKEwiajdSpkunwAhVNbJQKHTQVCLAQMygDegUIARCwAQ..i&docid=0DQ6bqrWTFgN1M&w=640&h=533&q=%EA%B3%A0%EC%96%91%EC%9D%B4&ved=2ahUKEwiajdSpkunwAhVNbJQKHTQVCLAQMygDegUIARCwAQ";
    String TOY_MAKER = "NIKE";
    String ADD_POSIX = "Second";


    List<Toy> toys;
    Toy toy;

    @BeforeEach
    void setUp() {
        toyService = mock(ToyService.class);
        controller = new ToyController(toyService);
    }


    @Nested
    @DisplayName("Describe: toyList 메소드는 ")
    class DescribeToyList {

        @Nested
        @DisplayName("Context: 장난감 목록이 존재할 때 ")
        class ContextToyList {

            @BeforeEach
            void setUp() {
                long size = 1L;
                setUpToys(size);
                given(controller.toyList()).willReturn(toys);
            }

            @Test
            @DisplayName("IT: 전체 장난감 리스트를 리턴한다.")
            void getToys() {
                assertThat(controller.toyList()).hasSize(1);
            }
        }

        @Nested
        @DisplayName("Context: 장난감 목록이 존재하지 않으면 ")
        class ContextEmptyList {

            @BeforeEach
            void setUp() {
                given(controller.toyList()).willReturn(new ArrayList<>());
            }


            @Test
            @DisplayName("IT: 빈 목록을 반환한다. ")
            void getToys() {
                assertThat(controller.toyList()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("Describe: detailToy 메소드는 ")
    class DescribeDetailToy {

        @BeforeEach
        void setUp() {
            long size = 1L;
            setUpToys(size);
            given(controller.detailToy(1L)).willReturn(toy);

        }

        @Nested
        @DisplayName("Context: 요청한 id 값이 존재하지 않으면")
        class ContextWithNotExistedId {

            @Test
            @DisplayName("IT: 예외를 던진다.")
            void getToyWithNotExistedId() {
                assertThat(controller.toyList()).isEmpty();
            }
        }

        @Nested
        @DisplayName("Context: 요청한 id 값이 존재하면")
        class ContextWithExistedId {

            @Test
            @DisplayName("IT: 해당 장난감을 리턴한다.")
            void getToyWithExistedId() {
                assertThat(controller.detailToy(1L)).isEqualTo(toy);
            }
        }
    }

    @Nested
    @DisplayName("Describe: create 메소드는 ")
    class DescribeCreate {

        @Nested
        @DisplayName("Context: 상품이 주어지면 ")
        class ContextCreate {

            private Toy givenToy;

            @BeforeEach
            void serUp() {
                givenToy = generateToy(1L);
                given(toyService.createToy(any(Toy.class)))
                        .will(invocation -> invocation.getArgument(0));
            }

            @Test
            @DisplayName("IT: 상품을 저장하고, 리턴한다.")
            void CreateNewToy() {
                Toy toy = controller.create(givenToy);
                assertThat(toy).isEqualTo(givenToy);
                verify(toyService).createToy(givenToy);
            }
        }
    }

    @Nested
    @DisplayName("Describe: Patch 메소드는 ")
    class DescribePatch {

        Toy generatedToy;

        @BeforeEach
        void setUp() {
            Long id = 1L;
            generatedToy = generateToy(id);
        }

        @Nested
        @DisplayName("Context: 요청한 id가 유효하면 ")
        class ContextWithExistedId {

            private Toy source;

            @BeforeEach
            void setUp() {
                source = new Toy();
                source.setName("UpdatedToy");
                given(toyService.updateToy(eq(1L), any(Toy.class)))
                        .will(invocation -> invocation.getArgument(1));
            }

            @Test
            @DisplayName("IT: 해당 장난감의 정보를 수정한다.")
            void updateToy() {
                Toy toy = controller.patch(1L, source);
                assertThat(toy.getName()).isEqualTo("UpdatedToy");
                verify(toyService).updateToy(1L, source);
            }
        }


        @Nested
        @DisplayName("Context: 요청한 id가 유효하지 않으면 ")
        class ContextWithNotExistedId {
        }
    }
//    @Test
//    void delete() {
//    }

    private List<Toy> setUpToys(long toysSize) {
        toys = new ArrayList<>();

        for (long i = 1; i < toysSize + 1; i++) {
            toys.add(generateToy(i));
        }
        return toys;
    }

    private Toy generateToy(Long id) {
        Toy toy = new Toy();
        toy.setId(id);
        toy.setName(TOY_NAME);
        toy.setPrice(TOY_PRICE);
        toy.setImageUrl(TOY_IMAGE_URL);
        toy.setMaker(TOY_MAKER);
        return toy;
    }
}