package com.codesoom.assignment.application;

import com.codesoom.assignment.Task.TaskNotFoundException;
import com.codesoom.assignment.Toy.application.ToyService;
import com.codesoom.assignment.Toy.domain.Toy;
import com.codesoom.assignment.Toy.domain.ToyJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
@DisplayName("Describe: ToyService class")
class ToyServiceTest {
    private ToyJpaRepository toyJpaRepository;
    private ToyService toyService;
    private List<Toy> toys;
    private Toy toy;
    private Toy toy2;

    String TOY_NAME = "CAT TOWER";
    Long TOY_PRICE = 10000L;
    String TOY_IMAGE_URL = "https://www.google.com/imgres?imgurl=https%3A%2F%2Fcdn.hellodd.com%2Fnews%2Fphoto%2F202005%2F71835_craw1.jpg&imgrefurl=https%3A%2F%2Fwww.hellodd.com%2Fnews%2FarticleView.html%3Fidxno%3D71835&tbnid=suDfwSE37I-hmM&vet=12ahUKEwiajdSpkunwAhVNbJQKHTQVCLAQMygDegUIARCwAQ..i&docid=0DQ6bqrWTFgN1M&w=640&h=533&q=%EA%B3%A0%EC%96%91%EC%9D%B4&ved=2ahUKEwiajdSpkunwAhVNbJQKHTQVCLAQMygDegUIARCwAQ";
    String TOY_MAKER = "NIKE";
    String ADD_POSIX = "Second";

    @BeforeEach
    void setUp() {
        toyJpaRepository = mock(ToyJpaRepository.class);
        toyService = new ToyService(toyJpaRepository);
        toys = new ArrayList<>();

        toy = new Toy();
        toy.setName(TOY_NAME);
        toy.setPrice(TOY_PRICE);
        toy.setImageUrl(TOY_IMAGE_URL);
        toy.setMaker(TOY_MAKER);

        toy2 = new Toy();
        toy2.setName(TOY_NAME + ADD_POSIX);
        toy2.setPrice(TOY_PRICE);
        toy2.setImageUrl(TOY_IMAGE_URL + ADD_POSIX);
        toy2.setMaker(TOY_MAKER + ADD_POSIX);
    }


    @Nested
    @DisplayName("Describe: GET method")
    class DescribeGetToy {

        @Nested
        @DisplayName("context: when request with No Id")
        class ContextWithNoId {

            @BeforeEach
            void registerToy() {
                toyService.createToy(toy);
                toys.add(toy);

                toyService.createToy(toy2);
                toys.add(toy2);

                given(toyJpaRepository.findAll()).willReturn(toys);
            }

            @Test
            @DisplayName("It: returns whole toy list")
            void getAllToys() {
                assertThat(toyService.getAllToy()).hasSize(toys.size());
                assertThat(toyService.getAllToy()).size().isEqualTo(2);

                // findAll()이 2번 실행됐음을 테스트하는 방법
//                verify(toyJpaRepository).findAll();
            }
        }

        @Nested
        @DisplayName("context: when request with Id")
        class ContextWithId {

            @BeforeEach
            void registerToy() {
                toyService.createToy(toy2);
                given(toyJpaRepository.findById(2L))
                        .willReturn(Optional.of(toy2));
            }

            @Test
            @DisplayName("It: returns Toy of requested id")
            void getAllToys() {
                assertThat(toyService.getToy(2L).getName())
                        .isEqualTo(TOY_NAME + ADD_POSIX);
                verify(toyJpaRepository).findById(2L);
            }
        }
    }

    @Nested
    @DisplayName("Describe: POST Method")
    class DescribeCreateToy {

        @BeforeEach
        void setUp() {

            // 이게 무슨 라인인지
            given(toyJpaRepository.save(any(Toy.class))).will(invocation -> {
                Toy toy = invocation.getArgument(0); // getArgument 의 0, 첫번째 index 는 any(Toy.class) 이다.
                toy.setId(1L); // 이거 없으면 에러, 왜 get 할 땐 id 안정해줬는데, 얘는 왜..?
                toy.setName(TOY_NAME);
                toy.setPrice(TOY_PRICE);
                toy.setImageUrl(TOY_IMAGE_URL);
                toy.setMaker(TOY_MAKER);

                // 에러: Index 1 out of bounds for length 1
//                Toy toy2 = invocation.getArgument(1);

                return toy;
            });
        }

        @Nested
        @DisplayName("context: when field of Toy is written")
        class ContextCreateToy {

            @BeforeEach
            void setUp() {
                toy = toyService.createToy(toy);
            }

            @Test
            @DisplayName("IT: Create New Toy and return")
            void createToy() {
                assertThat(toy.getId()).isEqualTo(1L);
                assertThat(toy.getName()).isEqualTo(TOY_NAME);

                verify(toyJpaRepository).save(any(Toy.class));
            }
        }
    }

    @Nested
    @DisplayName("Describe: Delete method")
    class DescribeDelete {

        @BeforeEach
        void setUp() {
            toyService.createToy(toy);
            // 나는 계속 given 구문을 빼먹어서 toy Not Found : 1 이 발생한다.
            // + findById에 3L을 넣어도 작동이 된다. 안돼야 하는 거 아닌가..?
            given(toyJpaRepository.findById(1L))
                    .willReturn(Optional.of(toy));
        }

        @Nested
        @DisplayName("Context: when id is provided")
        class ContextDeleteWithId {

            @Test
            @DisplayName("Context: delete with existed Id")
            void deleteWithId() {
                toyService.deleteToy(1L);
                assertThat(toyService.getAllToy()).isEmpty();
                verify(toyJpaRepository).delete(any(Toy.class));
                verify(toyJpaRepository).findById(1L);
            }
        }

        @Nested
        @DisplayName("Context: when invalid id is provided")
        class ContextDeleteWithNotExistedId {

            @Test
            @DisplayName("Context: delete with not existed Id")
            void deleteWithNotExistedId() {

                // not existing id를 냅다 넣어버리면 바로 에러터진다. -> 그래서 assertThatThrownBy()의 인자로 넣는 것.
//                toyService.deleteToy(3L);
//                verify(toyJpaRepository).delete(any(Toy.class));

                assertThatThrownBy(() -> toyService.deleteToy(-1L))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }
    }
}
