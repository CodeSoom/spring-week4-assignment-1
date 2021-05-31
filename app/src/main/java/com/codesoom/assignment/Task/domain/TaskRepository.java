package com.codesoom.assignment.Task.domain;

import java.util.List;
import java.util.Optional;

public interface TaskRepository{

    Optional<Task> findById(Long newId);

    Task save(Task task);

    List<Task> findAll();

    void delete(Task task);
}

