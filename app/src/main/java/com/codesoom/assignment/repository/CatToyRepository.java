package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.CatToy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatToyRepository extends JpaRepository<CatToy, Long> {
}
