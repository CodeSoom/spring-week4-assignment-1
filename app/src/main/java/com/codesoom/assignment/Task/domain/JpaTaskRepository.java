package com.codesoom.assignment.Task.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Primary
public interface JpaTaskRepository extends TaskRepository, CrudRepository<Task, Long> {

    Optional<Task> findById(Long newId);

    Task save(Task task);

    List<Task> findAll();

    void delete(Task task);


}
