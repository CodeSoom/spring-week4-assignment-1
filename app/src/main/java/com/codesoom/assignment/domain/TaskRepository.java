package com.codesoom.assignment.domain;

import com.codesoom.assignment.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TaskRepository {
    private ArrayList<Task> tasks = new ArrayList<>();
    private Long newId = 0L;

    public List<Task> findAll() {
        return tasks.stream().collect(Collectors.toList());
    }

    public Task find(Long id) {
        return null;
    }

    public Task save(Task task) {
        return null;
    }

    public void add(Task task) {
        task.setId(generateId());
        tasks.add(task);
    }

    private Long generateId() {
        newId += 1;
        return newId;
    }
}
