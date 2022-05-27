package com.codesoom.assignment.domain;

import com.codesoom.assignment.models.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long>{

    List<Task> findAll();

    Task find(Long id);

    Task save(Task task);

    Task remove(Task task);

}