package com.codesoom.assignment.repositories;

import com.codesoom.assignment.models.CatToy;
import com.codesoom.assignment.models.Toy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        private final List<Toy> toys = new ArrayList<>();

        @Override
        public List<Toy> findAll() {
            return toys;
        }

        @Override
        public Optional<Toy> findById(Long id) {
            if (toys.size() < id + 1) {
                return Optional.empty();
            }
            final Toy toy = toys.get(id.intValue());
            return Optional.of(toy);
        }

        @Override
        public Toy save(Toy toy) {
            final Long id = Integer.toUnsignedLong(toys.size());
            final Toy newToy = new CatToy(id, toy.name(), toy.brand(), toy.price(), toy.imageURL());

            toys.add(newToy);
            return newToy;
        }

        @Override
        public void deleteById(Long id) {
            toys.remove(id.intValue());
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
