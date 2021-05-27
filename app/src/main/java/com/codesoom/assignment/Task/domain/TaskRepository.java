package com.codesoom.assignment.Task.domain;

import java.util.List;
import java.util.Optional;

//@Component
//@Repository
public interface TaskRepository{

//    private List<Task> tasks = new ArrayList<>();
//    private Long newId = 0L;

    Optional<Task> findById(Long newId);

    Task save(Task task);

    List<Task> findAll();

    void delete(Task task);
}

