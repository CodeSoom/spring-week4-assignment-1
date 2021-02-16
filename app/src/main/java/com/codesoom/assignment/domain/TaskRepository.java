package com.codesoom.assignment.domain;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();

    Task find(Long id);

    Task save(Task task);

    Task remove(Task task);
}
