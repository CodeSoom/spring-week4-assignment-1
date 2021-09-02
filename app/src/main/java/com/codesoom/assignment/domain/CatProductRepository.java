package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public class CatProductRepository extends JpaRepository<CatProduct, Long> {


  List<CatProduct> findAll();


  Optional<CatProduct> findCatProductBy(Long id);
}
