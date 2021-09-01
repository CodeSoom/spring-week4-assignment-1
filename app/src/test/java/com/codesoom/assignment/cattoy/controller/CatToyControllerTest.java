package com.codesoom.assignment.cattoy.controller;

import com.codesoom.assignment.ProvideInvalidCatToyArguments;
import com.codesoom.assignment.cattoy.application.CatToyService;
import com.codesoom.assignment.common.application.ProductService;
import com.codesoom.assignment.cattoy.domain.CatToy;
import com.codesoom.assignment.common.domain.Product;
import com.codesoom.assignment.cattoy.exception.CatToyInvalidFieldException;
import com.codesoom.assignment.cattoy.exception.CatToyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;

import static com.codesoom.assignment.Constant.IMAGE_URL;
import static com.codesoom.assignment.Constant.MAKER;
import static com.codesoom.assignment.Constant.NAME;
import static com.codesoom.assignment.Constant.OTHER_IMAGE_URL;
import static com.codesoom.assignment.Constant.OTHER_MAKER;
import static com.codesoom.assignment.Constant.OTHER_NAME;
import static com.codesoom.assignment.Constant.OTHER_PRICE;
import static com.codesoom.assignment.Constant.PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@DisplayName("CatToyController 컨트롤러 테스트")
class CatToyControllerTest {

    private CatToyController controller;
    private ProductService<CatToy> catToyService;

    private CatToy catToy;
    private CatToy otherCatToy;

    @BeforeEach
    void setUp() {
        catToyService = mock(CatToyService.class);

        controller = new CatToyController(catToyService);

        setUpFixture();
    }

    private void setUpFixture() {
        catToy = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);
        ReflectionTestUtils.setField(catToy, "id", 1L);

        otherCatToy = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);

        given(catToyService.findAll())
                .willReturn(new ArrayList<>())
                .willReturn(Collections.singletonList(catToy));

        given(catToyService.findById(1L)).willReturn(catToy);

        given(catToyService.findById(100L)).willThrow(new CatToyNotFoundException(100L));

        given(catToyService.save(any(CatToy.class))).will(invocation -> {
            final CatToy args = invocation.getArgument(0);

            if (!args.equals(catToy)) {
                throw new CatToyInvalidFieldException();
            }

            ReflectionTestUtils.setField(args, "id", 1L);
            return args;
        });

        given(catToyService.updateProduct(eq(1L), any(CatToy.class))).will(invocation -> {
            CatToy catToy = invocation.getArgument(1, CatToy.class);
            if (!catToy.equals(otherCatToy)) {
                throw new CatToyInvalidFieldException();
            }
            return otherCatToy;
        });

        given(catToyService.updateProduct(eq(100L), any(CatToy.class)))
                .willThrow(new CatToyNotFoundException(100L));
    }

    @DisplayName("장난감 정보가 없더라도 전체 목록을 조회할 수 있습니다.")
    @Test
    void findAllWithoutToy() {
        assertThat(controller.findAll()).isEmpty();

        verify(catToyService).findAll();
    }

    @DisplayName("장난감 정보가 있을 경우에도 전체 목록을 조회할 수 있습니다.")
    @Test
    void findAllWithToy() {
        assertThat(controller.findAll()).isEmpty();

        controller.create(catToy);
        verify(catToyService).save(catToy);

        assertThat(controller.findAll()).hasSize(1);
        verify(catToyService, atLeast(2)).findAll();
    }

    @DisplayName("존재하는 식별자를 이용해 장난감 상세정보를 조회할 수 있습니다.")
    @Test
    void findByExistsId() {
        final Product foundCatToy = controller.findById(1L);

        assertThat(foundCatToy).isEqualTo(catToy);
        assertThat(foundCatToy.getName()).isEqualTo(catToy.getName());

        verify(catToyService).findById(1L);
    }

    @DisplayName("존재하지 않는 식별자를 이용해 장난감 상세정보를 조회할 경우 예외가 발생합니다.")
    @Test
    void findByNotExistsId() {
        assertThatThrownBy(() -> controller.findById(100L))
                .isInstanceOf(CatToyNotFoundException.class);
    }

    @DisplayName("새로운 장난감을 생성할 수 있습니다.")
    @Test
    void createWithCatToy() {
        final CatToy savedCatToy = controller.create(catToy);

        assertThat(savedCatToy.getId()).isNotNull();
        assertThat(savedCatToy).isEqualTo(catToy);
    }

    @DisplayName("유효하지 못한 장난감 정보를 생성하려 하면 예외가 발생합니다.")
    @ParameterizedTest
    @ArgumentsSource(ProvideInvalidCatToyArguments.class)
    void createInvalidCatToy(CatToy target) {
        assertThatThrownBy(() -> controller.create(target))
                .isInstanceOf(CatToyInvalidFieldException.class);
    }

    @DisplayName("존재하는 식별자의 장난감 정보를 수정할 수 있습니다.")
    @Test
    void updateExistsIdWithValidCatToy() {
        final Product foundCatToy = controller.findById(1L);

        final Product updatedCatToy = controller.updateCatToy(foundCatToy.getId(),
                CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL));

        assertThat(updatedCatToy.getName()).isEqualTo(OTHER_NAME);
        assertThat(updatedCatToy.getMaker()).isEqualTo(OTHER_MAKER);
        assertThat(updatedCatToy.getPrice()).isEqualTo(OTHER_PRICE);
        assertThat(updatedCatToy.getImageUrl()).isEqualTo(OTHER_IMAGE_URL);


    }

    @DisplayName("존재하는 식별자의 장난감 정보를 잘 못된 정보로 수정하려 하면 예외가 발생합니다.")
    @ParameterizedTest
    @ArgumentsSource(ProvideInvalidCatToyArguments.class)
    void updateExistsIdWithInvalidCatToy(CatToy target) {
        final Product foundCatToy = controller.findById(1L);

        assertThatThrownBy(() -> controller.updateCatToy(foundCatToy.getId(), target))
                .isInstanceOf(CatToyInvalidFieldException.class);

    }

    @DisplayName("존재하지 않는 식별자의 장난감 정보를 수정할 수 없습니다.")
    @Test
    void updateNotExistsId() {
        assertThatThrownBy(() -> controller.updateCatToy(100L, otherCatToy))
                .isInstanceOf(CatToyNotFoundException.class);
    }

    @DisplayName("존재하는 식별자의 장난감 정보를 삭제할 수 있습니다.")
    @Test
    void deleteExistsId() {
        controller.deleteCatToy(1L);

        verify(catToyService).findById(1L);
        verify(catToyService).deleteProduct(catToy);
    }

    @DisplayName("존재하지 않는 식별자의 장난감 정보를 삭제할 수 없습니다.")
    @Test
    void deleteNotExistsId() {
        assertThatThrownBy(()-> controller.deleteCatToy(100L))
                .isInstanceOf(CatToyNotFoundException.class);
    }

}
