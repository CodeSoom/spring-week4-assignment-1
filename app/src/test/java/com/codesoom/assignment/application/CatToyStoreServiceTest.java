package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.exception.NoDataException;
import com.codesoom.assignment.repository.CatToyStoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CatToyStoreServiceTest {

    // @Autowired 를 하지 않는 이유.
    // Autowired를 할 경우 실제 스프링 어플리케이션 컨텍스트를 읽어오기에
    // catToyStoreService는 mocking된 catToyStoreRepository를 의존하는게 아닌 실제 catToyStoreRepository를 의존하게 됨.
    private CatToyStoreService catToyStoreService;
    
    private CatToyStoreRepository catToyStoreRepository;

    private static final Long DEFAULT_ID = 1L;
    private static final Long CREATE_ID = 2L;
    private static final String DEFAULT_NAME = "첫번째 고양이 장난감";
    private static final Long INVALID_ID = 100L;
    private static final String CREATE_NAME = "새로 생성된 고양이 장난감";
    private static final String UPDATE_NAME = "수정된 고양이 장난감";

    @BeforeEach
    void setUp(){
        this.catToyStoreRepository = mock(CatToyStoreRepository.class);
        catToyStoreService = new CatToyStoreService(catToyStoreRepository);
        createDefaultCatToy();
        setUpFixtures();
    }
    
    void createDefaultCatToy(){
        List<CatToy> list = new ArrayList<>();

        CatToy catToy = new CatToy();
        catToy.setId(DEFAULT_ID);
        catToy.setName(DEFAULT_NAME);
        catToy.setPrice(1000);
        catToy.setMaker("첫번째 브랜드");
        catToy.setImageUrl("https://avatars.githubusercontent.com/u/9374562?s=400&v=4");

        list.add(catToy);

        given(catToyStoreRepository.findById(DEFAULT_ID))
                .willReturn(Optional.of(catToy));

        given(catToyStoreRepository.findAll())
                .willReturn(list);

    }

    void setUpFixtures(){
        given(catToyStoreRepository.findById(INVALID_ID))
                .willReturn(Optional.ofNullable(null));

        given(catToyStoreRepository.save(any(CatToy.class)))
                .will(invocation -> {
                    CatToy catToy = invocation.getArgument(0);
                    catToy.setId(CREATE_ID);
                    return catToy;
                });
    }
    @Test
    @DisplayName("리스트 조회")
    void getList() {

        /*다음과 같이 assertThat 안에 테스트 응답 값을 바로 받지말자.
        hasSize 뿐 아니라 실제 값도 비교해야할 경우 catToyStoreService.list를 한번 더 호출해야함.
        이때 verify 절에서 해당 메서드를 두번 호출했다는 에러가 발생함.*/
        //assertThat(catToyStoreService.list()).hasSize(1);

        List<CatToy> list = catToyStoreService.list();

        assertThat(list).hasSize(1);
        assertThat(list.get(0).getId()).isEqualTo(DEFAULT_ID);
        assertThat(list.get(0).getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @DisplayName("유효 ID에 대한 상세조회")
    void getDetailWithValidId() {

        CatToy catToy = catToyStoreService.detail(DEFAULT_ID);

        assertThat(catToy.getId()).isEqualTo(DEFAULT_ID);
        assertThat(catToy.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 상세조회")
    void getDetailWithInvalidId() {

        assertThatThrownBy(() -> catToyStoreService.detail(INVALID_ID))
                .isInstanceOf(NoDataException.class);

    }

    @Test
    @DisplayName("생성")
    void create() {
        CatToy resource = new CatToy();
        resource.setName(CREATE_NAME);
        resource.setPrice(2000);
        resource.setMaker("두번째 브랜드");
        resource.setImageUrl("https://avatars.githubusercontent.com/u/9374562?s=400&v=4");

        CatToy catToy = catToyStoreService.create(resource);

        // 서비스 로직에서 CatToy를 새로 생성하므로 any 사용
        verify(catToyStoreRepository).save(any(CatToy.class));

        assertThat(catToy.getId()).isEqualTo(CREATE_ID);
        assertThat(catToy.getName()).isEqualTo(CREATE_NAME);
        assertThat(catToy.getPrice()).isEqualTo(2000);
    }

    @Test
    @DisplayName("유효 ID에 대한 수정")
    void updateWithValidId() {

        CatToy resource = new CatToy();
        resource.setName(UPDATE_NAME);
        resource.setPrice(3000);
        resource.setMaker("수정된 브랜드");
        resource.setImageUrl("https://avatars.githubusercontent.com/u/9374562?s=400&v=4");

        CatToy catToy = catToyStoreService.update(DEFAULT_ID,resource);

        assertThat(catToy.getName()).isEqualTo(UPDATE_NAME);
        assertThat(catToy.getMaker()).isEqualTo("수정된 브랜드");

    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 수정")
    void updateWithInvalidId() {

        CatToy resource = new CatToy();
        resource.setName(UPDATE_NAME);
        resource.setPrice(3000);
        resource.setMaker("수정된 브랜드");
        resource.setImageUrl("https://avatars.githubusercontent.com/u/9374562?s=400&v=4");

        assertThatThrownBy(()->catToyStoreService.update(INVALID_ID,resource))
                .isInstanceOf(NoDataException.class);
    }

    @Test
    @DisplayName("유효 ID에 대한 삭제")
    void deleteWithValidId() {

        catToyStoreService.delete(DEFAULT_ID);

        verify(catToyStoreRepository).deleteById(DEFAULT_ID);

    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 삭제")
    void deleteWithInvalidId() {

        assertThatThrownBy(()->catToyStoreService.delete(INVALID_ID))
                .isInstanceOf(NoDataException.class);


    }
}