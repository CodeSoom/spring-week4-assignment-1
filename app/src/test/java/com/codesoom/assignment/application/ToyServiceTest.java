package com.codesoom.assignment.application;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
@DisplayName("Describe: ToyService class")
class ToyServiceTest {
    private ToyJpaRepository toyJpaRepository;
    private ToyService toyService;

    String TOY_NAME = "CAT TOWER";
    Long TOY_PRICE = 10000L;
    String TOY_IMAGE_URL = "https://www.google.com/imgres?imgurl=https%3A%2F%2Fcdn.hellodd.com%2Fnews%2Fphoto%2F202005%2F71835_craw1.jpg&imgrefurl=https%3A%2F%2Fwww.hellodd.com%2Fnews%2FarticleView.html%3Fidxno%3D71835&tbnid=suDfwSE37I-hmM&vet=12ahUKEwiajdSpkunwAhVNbJQKHTQVCLAQMygDegUIARCwAQ..i&docid=0DQ6bqrWTFgN1M&w=640&h=533&q=%EA%B3%A0%EC%96%91%EC%9D%B4&ved=2ahUKEwiajdSpkunwAhVNbJQKHTQVCLAQMygDegUIARCwAQ";
    String TOY_MAKER = "NIKE";
    String CREATE_POSIX = "meow";

    @BeforeEach
    void setUp() {
        toyJpaRepository = mock(ToyJpaRepository.class);

        toyService = new ToyService(toyJpaRepository);

        setUpFixtures();
        setUpSave();
    }

    void setUpFixtures() {
        List<Toy> toys = new ArrayList<>();

        Toy toy = new Toy();
        toy.setName(TOY_NAME);
        toy.setPrice(TOY_PRICE);
        toy.setImageUrl(TOY_IMAGE_URL);
        toy.setMaker(TOY_MAKER);

        toys.add(toy);

        given(toyJpaRepository.findAll()).willReturn(toys);
    }

    void setUpSave() {
        given(toyJpaRepository.save(any(Toy.class))).will(invocation -> {
            Toy toy = invocation.getArgument(0);
            toy.setId(2L);
            return toy;
        });
    }

    @Nested
    @DisplayName("Describe: Get method")
    class getToys {


        @Nested
        @DisplayName("context: with No Id")
        class Context_with_no_id {

            @Test
            void getAllToys() {
                List<Toy> toys = toyService.getAllToy();
                verify(toyJpaRepository).findAll();
            }
        }

        @Nested
        @DisplayName("context: with specific id")
        class Context_with_id {

            @Test
            void getToyWithSpecificId() {
                Toy toy = toyService.getToyById(1L);
                assertThat(toy.getName()).isEqualTo(TOY_NAME);
                verify(toyJpaRepository).findById(1L);
            }
        }
    }


    @Test
    void getToyById() {
    }

    @Test
    void createToy() {
    }

    @Test
    void updateToy() {
    }

    @Test
    void deleteToy() {
    }
}