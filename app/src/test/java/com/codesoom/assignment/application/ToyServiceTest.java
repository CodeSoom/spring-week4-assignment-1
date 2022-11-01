package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.exceptions.ToyNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@DisplayName("ToyService의")
class ToyServiceTest {

    private static final String NAME = "name";
    private static final String MAKER = "maker";
    private static final Integer PRICE = 100;
    private static final String IMAGE_URL = "http://localhost:8080/original";
    private static final String UPDATED_NAME = "updated name";
    private static final String UPDATED_MAKER = "updated maker";
    private static final Integer UPDATED_PRICE = 500;
    private static final String UPDATED_IMAGE_URL = "http://www.localhost:8080/updated";

    @Autowired
    private ToyService service;

    private Long getExistingId() {
        return service.getToys().get(0).getId();
    }

    @Test
    @DisplayName("getToys()는 등록된 장난감이 없을 때 빈 리스트를 리턴한다. ")
    void given_none_toys_registered_when_getToys_invoked_then_empty_list_returned() {
        List<Toy> list = service.getToys();

        assertThat(list).isEmpty();
    }

    @Test
    @DisplayName("getToys()는 등록된 장난감이 있을 때 비어 있지 않을 리스트를 리턴한다.")
    void given_toys_registered_exists_when_getToys_invoked_then_not_empty_list_returned() {
        service.create(new Toy());

        List<Toy> list = service.getToys();

        assertThat(list).isNotEmpty();
    }

    @Test
    @DisplayName("getToy()는 등록된 장난감의 id를 인자로 호출하면 해당 장난감을 리턴한다.")
    void when_getToy_invoked_with_registered_id_then_corresponding_toy_returned() {
        service.create(new Toy());

        Toy toy = service.getToy(1L);

        assertThat(toy.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getToy()는 등록되지 않은 장난감의 id를 인자로 호출하면 예외를 발생시킨다.")
    void when_getToy_invoked_with_not_registered_id_then_exception_thrown() {
        service.create(new Toy());

        assertThatThrownBy(() -> service.getToy(100L))
                .isInstanceOf(ToyNotFoundException.class);
    }

    @Test
    @DisplayName("create()는 인자로 주어진 toy 객체를 repository에 저장한다.")
    void when_create_invoked_toy_then_that_toy_saved_in_repository() {
        int oldSize = service.getToys().size();

        service.create(new Toy());

        int newSize = service.getToys().size();
        assertThat(newSize - oldSize).isEqualTo(1);
    }

    @Test
    @DisplayName("create()는 고유 id가 부여된 toy 객체를 할당한다.")
    void when_create_invoked_then_returns_toy_with_unique_id() {
        Set<Long> idSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Toy toy = service.create(new Toy());
            idSet.add(toy.getId());
        }

        assertThat(idSet.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("update()는 존재하는 id를 인자로 호출하면 해당 toy 객체의 정보를 수정한다.")
    void when_update_invoked_with_existing_id_then_corresponding_toy_is_updated() {
        service.create(new Toy(NAME, MAKER, PRICE, IMAGE_URL));

        Toy src = new Toy(UPDATED_NAME, UPDATED_MAKER, UPDATED_PRICE, UPDATED_IMAGE_URL);
        Long id = getExistingId();
        Toy toy = service.update(id, src);

        assertThat(toy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(toy.getMaker()).isEqualTo(UPDATED_MAKER);
        assertThat(toy.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(toy.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @DisplayName("update()는 존재하지 않는 id를 인자로 호출하면 예외를 발생시킨다.")
    void when_update_invoked_with_not_existing_id_then_exception_thrown() {
        service.create(new Toy(NAME, MAKER, PRICE, IMAGE_URL));

        Toy src = new Toy(UPDATED_NAME, UPDATED_MAKER, UPDATED_PRICE, UPDATED_IMAGE_URL);
        assertThatThrownBy(() -> service.update(100L, src))
                .isInstanceOf(ToyNotFoundException.class);
    }

    @Test
    @DisplayName("delete()는 존재하는 id를 인자로 호출하면 해당 toy는 삭제된다.")
    void when_update_invoked_with_existing_id_then_corresponding_toy_is_deleted() {
        service.create(new Toy());

        Long id = getExistingId();

        service.delete(id);

        List<Toy> toys = service.getToys();
        assertThat(toys).isEmpty();
    }
}
