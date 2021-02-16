package com.codesoom.assignment.Infra;

import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaTaskRepository
        extends TaskRepository, CrudRepository<Task, Long> {
    List<Task> findAll();

    Task find(Long id);

    Task save(Task task);

    void delete(Task task);
}
