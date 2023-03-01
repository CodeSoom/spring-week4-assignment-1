package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.CatToy;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
class CatToyStoreRepositoryTest {

    @Autowired
    private CatToyStoreRepository catToyStoreRepository;
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_NAME = "첫번째 고양이 장난감";
    private static final Long INVALID_ID = 100L;
    private static final Long CREATE_ID = 2L;
    private static final String CREATE_NAME = "두번째 고양이 장난감";


    @BeforeEach
    void setUp(){
        CatToy catToy = new CatToy();
        catToy.setId(DEFAULT_ID);
        catToy.setName(DEFAULT_NAME);
        catToy.setPrice(1000);
        catToy.setMaker("첫번째 브랜드");
        catToy.setImageUrl("https://avatars.githubusercontent.com/u/9374562?s=400&v=4");

        catToyStoreRepository.save(catToy);
    }


    @Test
    @DisplayName("리스트 조회")
    void findAll() {
        List<CatToy> list = catToyStoreRepository.findAll();
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getId()).isEqualTo(DEFAULT_ID);
        assertThat(list.get(0).getName()).isEqualTo(DEFAULT_NAME);

    }

    @Test
    @DisplayName("유효 ID에 대한 상세조회")
    void findByIdWithValidId() {
        CatToy catToy = catToyStoreRepository.findById(DEFAULT_ID).get();

        assertThat(catToy.getId()).isEqualTo(DEFAULT_ID);
        assertThat(catToy.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 상세조회")
    void findByIdWithInvalidId() {
        assertThat(catToyStoreRepository.findById(INVALID_ID)).isEmpty();

    }

    @Test
    @DisplayName("저장")
    void save() {
        CatToy catToy = new CatToy();
        catToy.setId(CREATE_ID);
        catToy.setName(CREATE_NAME);
        catToy.setPrice(2000);
        catToy.setMaker("두번째 브랜드");
        catToy.setImageUrl("https://avatars.githubusercontent.com/u/9374562?s=400&v=4");

        catToyStoreRepository.save(catToy);
        assertThat(catToyStoreRepository.findAll()).hasSize(2);
    }


    @Test
    @DisplayName("삭제")
    void deleteById() {
        catToyStoreRepository.deleteById(DEFAULT_ID);
        assertThat(catToyStoreRepository.findById(DEFAULT_ID)).isEmpty();
    }


    
}