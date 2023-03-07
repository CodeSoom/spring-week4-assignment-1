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
    private Long savedId;
    private static final String DEFAULT_NAME = "첫번째 고양이 장난감";
    private static final Long INVALID_ID = 100L;
    private static final Long CREATE_ID = 2L;
    private static final String CREATE_NAME = "두번째 고양이 장난감";


    @BeforeEach
    void setUp(){
        CatToy catToy = new CatToy();
        catToy.setName(DEFAULT_NAME);
        catToy.setPrice(1000);
        catToy.setMaker("첫번째 브랜드");
        catToy.setImageUrl("https://avatars.githubusercontent.com/u/9374562?s=400&v=4");

        CatToy savedCatToy =  catToyStoreRepository.save(catToy);
        savedId = savedCatToy.getId();
    }

    @AfterEach
    void end(){
        catToyStoreRepository.deleteAll();
    }
    @Test
    @DisplayName("리스트 조회 > 1개 리스트 조회 및 상품에 대한 id, name 일치")
    void findAll() {
        List<CatToy> list = catToyStoreRepository.findAll();
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getId()).isEqualTo(savedId);
        assertThat(list.get(0).getName()).isEqualTo(DEFAULT_NAME);

    }

    @Test
    @DisplayName("유효 ID에 대한 상세조회 > 조회 상품에 대한 id, name 일치")
    void findByIdWithValidId() {
        CatToy catToy = catToyStoreRepository.findById(savedId).get();

        assertThat(catToy.getId()).isEqualTo(savedId);
        assertThat(catToy.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 상세조회 > 비어있는 Optional 리턴")
    void findByIdWithInvalidId() {
        assertThat(catToyStoreRepository.findById(INVALID_ID)).isEmpty();

    }

    @Test
    @DisplayName("저장 > 1 증가된 리스트 개수 확인 (예상 : 2)")
    void save() {
        CatToy catToy = new CatToy();
        catToy.setName(CREATE_NAME);
        catToy.setPrice(2000);
        catToy.setMaker("두번째 브랜드");
        catToy.setImageUrl("https://avatars.githubusercontent.com/u/9374562?s=400&v=4");

        catToyStoreRepository.save(catToy);
        assertThat(catToyStoreRepository.findAll()).hasSize(2);
    }


    @Test
    @DisplayName("삭제 > 삭제한 ID에 대한 상세 조회 결과가 빈 Optional 인지 확인")
    void deleteById() {
        catToyStoreRepository.deleteById(savedId);
        assertThat(catToyStoreRepository.findById(savedId)).isEmpty();
    }


    
}