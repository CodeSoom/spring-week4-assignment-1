package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();

    Optional<Task> findById(Long id);

    Task save(Task task);

    void delete(Task task);
}
