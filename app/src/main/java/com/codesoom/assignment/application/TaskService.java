package com.codesoom.assignment.application;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private Long id;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) throws TaskNotFoundException {
        return taskRepository.find(id);
    }

    public Task createTask(Task source) throws TaskNotFoundException {
        Task task = taskRepository.find(id);
        return taskRepository.save(task);
    }

    public Task deleteTask(Long id) throws TaskNotFoundException {
        Task task = taskRepository.find(id);
        return taskRepository.remove(task);
    }
}
