package com.codesoom.assignment.repository;

import com.codesoom.assignment.entity.Toy;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ToyRepositoryTest {

    @Autowired
    private ToyRepository toyRepository;

    @BeforeEach
    void setUp() {
        toyRepository.deleteAll();
        Toy toy = new Toy(1L, "테스트1", 2000, "");
        toyRepository.save(toy);
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_find_all {
        @Nested
        @DisplayName("저장된 toy가 있다면")
        class Context_have_toy {
            @Test
            @DisplayName("toyList를 리턴한다")
            void it_returns_toyList() {
                assertThat(toyRepository.findAll()).hasSize(1);
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_find_by_id {

        @Nested
        @DisplayName("id가 존재하지 않는다면")
        class Context_have_no_id {
            @Test
            @DisplayName("비어있는 Optional을 리턴한다")
            void it_returns_empty_toyList() {
                assertThat(toyRepository.findById(100L)).isEqualTo(Optional.empty());
            }
        }

        @Nested
        @DisplayName("id가 존재한다면")
        class Context_have_id {
            @Test
            @DisplayName("해당 id를 가진 toy객체를 리턴한다")
            void it_returns_toyList() {
                assertThat(toyRepository.findById(1L).get()).isEqualTo(new Toy(1L, "테스트1", 2000, ""));
            }
        }
    }


    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("Toy객체가 주어질 때")
        class Context_have_toy {
            @Test
            @DisplayName("toy객체를 리턴한다")
            void it_returns_toy() {
                assertThat(toyRepository.save(new Toy(2L, "테스트2", 2000, "")).getBrand()).isEqualTo("테스트2");
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_delete_by_id {
        @Nested
        @DisplayName("id가 존재한다면")
        class Context_have_id {

            @Test
            @DisplayName("해당 id를 가진 toy객체를 삭제한다")
            void it_returns_toy() {
                toyRepository.deleteById(1L);
                assertThat(toyRepository.findAll()).hasSize(0);

            }
        }
    }

}