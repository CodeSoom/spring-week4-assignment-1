package com.codesoom.assignment.application;

import com.codesoom.assignment.CatToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CatToyServiceTest {
    private static final String TEST_NAME = "testName";
    private static final String TEST_MAKER = "testMaker";
    private static final Long TEST_PRICE = 5000L;
    private static final String TEST_IMAGE_PATH = "testImagePath.jpg";
    private static final String CREATE_POSTFIX = "...";
    private static final String UPDATE_POSTFIX = "!!!";

    private CatToyService catToyService;

    private CatToyRepository catToyRepository;

    @BeforeEach
    void setUp() {
        catToyRepository = mock(CatToyRepository.class);

        catToyService = new CatToyService(catToyRepository);

        setUpFixture();
        setUpSaveCatToy();
    }

    void setUpFixture() {
        List<CatToy> catToys = new ArrayList<>();

        CatToy catToy = new CatToy();
        catToy.setName(TEST_NAME);
        catToy.setMaker(TEST_MAKER);
        catToy.setPrice(TEST_PRICE);
        catToy.setImagePath(TEST_IMAGE_PATH);

        catToys.add(catToy);

        given(catToyRepository.findAll()).willReturn(catToys);

        given(catToyRepository.findById(1L)).willReturn(Optional.of(catToy));
        given(catToyRepository.findById(100L)).willReturn(Optional.empty());
    }

    void setUpSaveCatToy() {
        given(catToyRepository.save(any(CatToy.class))).will(invocation -> {
            CatToy catToy = invocation.getArgument(0);
            catToy.setId(2L);
            return catToy;
        });
    }

    @Test
    void getCatToys() {
        List<CatToy> catToys = catToyService.getCatToys();

        verify(catToyRepository).findAll();

        assertThat(catToys).hasSize(1);

        CatToy catToy = catToys.get(0);
        assertThat(catToy.getName()).isEqualTo(TEST_NAME);
    }

    @Test
    void getCatToyWithExistedId() {
        CatToy catToy = catToyService.getCatToy(1L);

        verify(catToyRepository).findById(1L);


        assertThat(catToy.getName()).isEqualTo(TEST_NAME);
    }

    @Test
    void getCatToyWithNotExistedId() {
        assertThatThrownBy(() -> catToyService.getCatToy(100L))
                .isInstanceOf(CatToyNotFoundException.class);

        verify(catToyRepository).findById(100L);
    }

    @Test
    void createCatToy() {
        CatToy source = new CatToy();
        source.setName(TEST_NAME + CREATE_POSTFIX);
        source.setMaker(TEST_MAKER + CREATE_POSTFIX);
        source.setPrice(TEST_PRICE + 1000L);
        source.setImagePath(CREATE_POSTFIX + TEST_IMAGE_PATH);

        CatToy catToy = catToyService.createCatToy(source);

        verify(catToyRepository).save(any(CatToy.class));

        assertThat(catToy.getId()).isEqualTo(2L);
        assertThat(catToy.getName()).isEqualTo(TEST_NAME + CREATE_POSTFIX);
    }

    @Test
    void updateCatToyWithExistedId() {
        CatToy source = new CatToy();
        source.setName(TEST_NAME + UPDATE_POSTFIX);

        CatToy catToy = catToyService.updateCatToy(1L, source);

        verify(catToyRepository).findById(1L);

        assertThat(catToy.getName()).isEqualTo(TEST_NAME + UPDATE_POSTFIX);
    }

    @Test
    void updateCatToyWithNotExistedId() {
        CatToy source = new CatToy();
        source.setName(TEST_NAME + UPDATE_POSTFIX);

        assertThatThrownBy(() -> catToyService.updateCatToy(100L, source))
                .isInstanceOf(CatToyNotFoundException.class);

        verify(catToyRepository).findById(100L);
    }

    @Test
    void deleteCatToyWithExistedId() {
        catToyService.deleteCatToy(1L);

        verify(catToyRepository).findById(1L);

        verify(catToyRepository).delete(any(CatToy.class));
    }

    @Test
    void deleteCatToyWithNotExistedId() {
        assertThatThrownBy(() -> catToyService.deleteCatToy(100L))
                .isInstanceOf(CatToyNotFoundException.class);

        verify(catToyRepository).findById(100L);
    }
}
