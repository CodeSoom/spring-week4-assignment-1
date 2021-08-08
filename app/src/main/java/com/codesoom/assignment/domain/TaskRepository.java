package com.codesoom.assignment.domain;

import com.codesoom.assignment.TaskNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository{

    List<Task> findAll();

    Optional<Task> findById(Long id);

    Task save(Task task);

    void delete(Task task);

}
