package com.codesoom.assignment.infra;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.models.Task;
import com.codesoom.assignment.models.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryTaskRepository extends TaskRepository {
    private final List<Task> tasks = new ArrayList<>();
    private Long newId = 0L;

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
        task.setId(generateID());

        tasks.add(task);

        return task;
    }

    @Override
    public Task remove(Task task) {
        tasks.remove(task);

        return task;
    }

    private Long generateID() {
        newId += 1;

        return newId;
    }

}
