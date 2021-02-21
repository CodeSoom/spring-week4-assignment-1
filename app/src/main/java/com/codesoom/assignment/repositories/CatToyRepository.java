package com.codesoom.assignment.repositories;

import com.codesoom.assignment.models.CatToy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CatToyRepository extends CrudRepository<CatToy, Long> {
    @Override
    List<CatToy> findAll();

    @Override
    Optional<CatToy> findById(Long id);

    @Override
    <S extends CatToy> S save(S toy);

    @Override
    void deleteById(Long id);

    final class Fake implements CatToyRepository {
        private final List<CatToy> toys = new ArrayList<>();

        @Override
        public List<CatToy> findAll() {
            return toys;
        }

        @Override
        public Optional<CatToy> findById(Long id) {
            if (toys.size() < id + 1) {
                return Optional.empty();
            }
            final CatToy toy = toys.get(id.intValue());
            return Optional.of(toy);
        }

        @Override
        public <S extends CatToy> S save(S toy) {
            final Long id = Integer.toUnsignedLong(toys.size());
            final CatToy newToy = new CatToy(id, toy.name(), toy.brand(), toy.price(), toy.imageURL());

            toys.add(newToy);
            return (S) newToy;
        }

        @Override
        public void deleteById(Long id) {
            toys.remove(id.intValue());
        }

        public Iterable<CatToy> findAllById(Iterable<Long> longs) {
            return null;
        }

        public boolean existsById(Long aLong) {
            return false;
        }

        public long count() {
            return 0;
        }

        public <S extends CatToy> Iterable<S> saveAll(Iterable<S> entities) {
            return null;
        }

        public void delete(CatToy entity) {
        }

        public void deleteAll(Iterable<? extends CatToy> entities) {
        }

        public void deleteAll() {
        }
    }
}
