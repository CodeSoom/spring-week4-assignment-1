package com.codesoom.repository;

import com.codesoom.domain.Toy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ToyRepository implements CrudRepository<Toy, Long> {

    private final Map<Long, Toy> toys = new HashMap<>();
    private  Long TOY_ID = 0L;

    @Override
    public List<Toy> findAll() {
        return new ArrayList<>(toys.values());
    }

    @Override
    public Toy save(Toy toy) {

        TOY_ID++;
        toy.setId(TOY_ID);
        toys.put(TOY_ID, toy);

        return toy;

    }

    @Override
    public Optional<Toy> findById(Long id) {

        if(toys.get(id) == null) Optional.empty();

        return Optional.of(toys.get(id));

    }

    @Override
    public void delete(Toy entity) {
        toys.remove(entity.getId());
    }

    /*
       아직 작성하지 않았습니다
     */
    @Override
    public <S extends Toy> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Toy> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {}

    @Override
    public void deleteAll(Iterable<? extends Toy> entities) {}

    @Override
    public void deleteAll() {}

}
