package com.codesoom.assignment.domain.repository;

import com.codesoom.assignment.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	@Override
	List<Product> findAll();

}
