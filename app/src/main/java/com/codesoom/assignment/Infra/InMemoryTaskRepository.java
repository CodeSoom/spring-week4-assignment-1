package com.codesoom.assignment.Infra;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryTaskRepository implements TaskRepository {
    private List<Task> tasks = new ArrayList<>();
    private Long newId = 0L;

    private Long generateId() {
        newId += 1;
        return newId;
    }

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public Task find(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Task save(Task task) {
        task.setId(generateId());
        tasks.add(task);
        return task;
    }

    @Override
    public Task remove(Task task) {
        tasks.remove(task);
        return task;
    }
}
