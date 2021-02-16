package com.codesoom.assignment.domain;

import com.codesoom.assignment.TaskNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    private List<Task> tasks = new ArrayList<>();

    public List<Task> findAll() {
        return tasks;
    }

    public Task find(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task save(Task task) {
        tasks.add(task);
        return task;
    }

    public Task remove(Task task) {
        tasks.remove(task);
        return task;
    }
}
