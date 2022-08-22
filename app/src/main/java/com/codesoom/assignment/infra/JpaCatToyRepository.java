package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.data.repository.CrudRepository;

public interface JpaCatToyRepository extends CatToyRepository, CrudRepository<CatToy, Long> {
}
