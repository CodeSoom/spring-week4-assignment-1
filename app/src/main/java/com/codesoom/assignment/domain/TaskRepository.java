package com.codesoom.assignment.domain;

import java.util.List;

public abstract class TaskRepository {
    public abstract List<Task> findAll();

    public abstract Task find(Long id);

    public abstract Task save(Task task);

    public abstract Task remove(Task task);
}
