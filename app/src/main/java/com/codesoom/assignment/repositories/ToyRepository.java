package com.codesoom.assignment.repositories;

import com.codesoom.assignment.models.CatToy;
import com.codesoom.assignment.models.Toy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface ToyRepository extends CrudRepository<Toy, Long> {
    @Override
    List<Toy> findAll();

    @Override
    Optional<Toy> findById(Long id);

    @Override
    Toy save(Toy toy);

    @Override
    void deleteById(Long id);

    final class Fake implements ToyRepository {
        private final CatToy givenCatToy1 = new CatToy(1L, "캣닢", "(주)고양이회사", 1000D, "https://cat.toy/cat-nip.png");
        private final CatToy givenCatToy2 = new CatToy(2L, "캣타워", "(주)고양이회사", 2000D, "https://cat.toy/cat-tower.png");

        @Override
        public List<Toy> findAll() {
            return new ArrayList<>(Arrays.asList(givenCatToy1, givenCatToy2));
        }

        @Override
        public Optional<Toy> findById(Long id) {
            return switch (id.intValue()) {
                case 1 -> Optional.of(givenCatToy1);
                case 2 -> Optional.of(givenCatToy2);
                default -> Optional.empty();
            };
        }

        @Override
        public Toy save(Toy toy) {
            return new CatToy(3L, toy.name(), toy.brand(), toy.price(), toy.imageURL());
        }

        @Override
        public void deleteById(Long id) {
        }

        public Iterable<Toy> findAllById(Iterable<Long> longs) {
            return null;
        }

        public boolean existsById(Long aLong) {
            return false;
        }

        public long count() {
            return 0;
        }

        public <S extends Toy> Iterable<S> saveAll(Iterable<S> entities) {
            return null;
        }

        public void delete(Toy entity) {
        }

        public void deleteAll(Iterable<? extends Toy> entities) {
        }

        public void deleteAll() {
        }
    }
}
