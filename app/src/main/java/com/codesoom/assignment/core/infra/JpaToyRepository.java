package com.codesoom.assignment.core.infra;

import com.codesoom.assignment.core.domain.Toy;
import com.codesoom.assignment.core.domain.ToyRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 고양이 장난감
 */
@Repository
public interface JpaToyRepository
        extends ToyRepository, CrudRepository<Toy, Long> {

    @Override
    public List<Toy> findAll();

}
