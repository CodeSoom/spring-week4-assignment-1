package com.codesoom.assignment.domain;

import com.codesoom.assignment.TaskNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private Long newId = 0L;

    public List<Task> findAll() {
        return tasks;
    }

    public Task find(Long id) throws TaskNotFoundException {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task save(Task task) {
        task.setId(generateId());
        tasks.add(task);
        return task;
    }

    public Task remove(Task task) {
        tasks.remove(task);
        return task;
    }

    public void add(Task task) {
        task.setId(generateId());
        tasks.add(task);
    }

    public Task addTitle(Long id) throws TaskNotFoundException {
        return tasks.stream()
                .filter(task -> task.getTitle().equals(id))
                .findFirst()
                .orElseThrow(()-> new TaskNotFoundException(id));
    }

    private Long generateId() {
        newId += 1;
        return newId;
    }
}
