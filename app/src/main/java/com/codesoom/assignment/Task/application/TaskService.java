package com.codesoom.assignment.Task.application;

import com.codesoom.assignment.Task.TaskNotFoundException;
import com.codesoom.assignment.Task.domain.Task;
import com.codesoom.assignment.Task.domain.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * TaskController 의 요청을 수행합니다.
 */
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     * TaskService 생성자에 TaskRepository 를 주입합니다.
     * @param taskRepository 생성자에 주입할 TaskRepository DAO
     */
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    /**
     * 모든 Task 를 리턴합니다.
     * @return 모든 Task 를 담고 있는 list
     */
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    /**
     * 요청한 id의 Task 를 리턴합니다.
     * @param id 요청 id
     * @return 요청한 id의 Task 객체
     */
    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    /**
     * 새로운 Task 를 생성합니다.
     * @param source Task 객체
     * @return 생성된 Task
     */
    public Task createTask(Task source) {
        Task task = new Task();
        task.setTitle(source.getTitle());

        return taskRepository.save(task);
    }

    /**
     * 기존 Task 를 업데이트 합니다.
     * @param id 요청 id
     * @param source Task 객체
     * @return 업데이트한 Task
     */
    public Task updateTask(Long id, Task source) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));;
        task.setTitle(source.getTitle());

        return task;
    }

    /**
     * 저장된 Task를 삭제합니다.
     * @param id 요청 id
     * @return 삭제한 Toy
     */
    public Task deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
        return task;
    }

}
