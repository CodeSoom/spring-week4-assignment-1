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

    /**
    * id에 해당하는 task를 찾아서 반환합니다.
    **/
    public Task getTask(Long id) throws TaskNotFoundException {
        return taskRepository.find(id);
    }


    /**
     * id에 해당하는 task를 찾아서, 새로운 타이틀 값을 넣고 저장된 타이틀 객체를 반환 합니다.
     **/
    public Task createTask(Long id) throws TaskNotFoundException {
        Task task = taskRepository.find(id);
        taskRepository.addTitle(id);
        return taskRepository.save(task);
    }

    /**
     * id에 해당하는 task를 찾아서 해당 태스크를 삭제하고, 삭제한 task를 반환합니다.
     **/
    public Task deleteTask(Long id) throws TaskNotFoundException {
        Task task = taskRepository.find(id);
        return taskRepository.remove(task);
    }
}
