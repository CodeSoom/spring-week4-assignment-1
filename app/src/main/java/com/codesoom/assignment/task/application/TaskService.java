package com.codesoom.assignment.task.application;

import com.codesoom.assignment.task.TaskNotFoundException;
import com.codesoom.assignment.task.domain.Task;
import com.codesoom.assignment.task.domain.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepo;

    public TaskService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<Task> getTasks() {
        return taskRepo.findAll();
    }

    public Task getTask(Long id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task source) {
        Task task = new Task();
        task.setTitle(source.getTitle());

        return taskRepo.save(task);
    }

    public Task updateTask(Long id, Task source) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(source.getTitle());

        return task;
    }

    public Task deleteTask(Long id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepo.delete(task);

        return task;
    }


}
