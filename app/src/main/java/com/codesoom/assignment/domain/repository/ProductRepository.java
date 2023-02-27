package com.codesoom.assignment.domain.repository;

import com.codesoom.assignment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.maker = :maker")
    List<Product> findByMaker(@Param("maker") String maker);

    @Modifying
    @Query("delete from Product p where p.maker = :maker")
    void deleteByMaker(@Param("maker") String maker);
}
