package com.codesoom.assignment.repository;

import com.codesoom.assignment.entity.Toy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToyRepository extends JpaRepository<Toy, Long> {
}
