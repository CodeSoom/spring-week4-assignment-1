package com.codesoom.assignment.application;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    /**
     * 요청한 id에 해당하는 Task를 찾아 리턴합니다.
     *
     * @param id 검색하고자 하는 Task id
     * @return 요청한 id에 해당하는 Task 반환
     */
    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    /**
     * 입력한 Task 값을 저장하고, 저장된 Task값을 리턴합니다.
     *
     * @param source 입력한 Task 값
     * @return 저장된 Task 값
     */
    public Task createTask(Task source) {
        Task task = new Task(source.getTitle());

        return taskRepository.save(task);
    }

    /**
     * 요청한 id에 해당하는 Task를 찾아 값을 변경한 후 리턴합니다.
     *
     * @param id 검색하고자 하는 Task id
     * @param source 수정 할 값
     * @return 수정완료된 Task 값 반환
     */
    public Task updateTask(Long id, Task source) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(source.getTitle());

        return task;
    }

    /**
     * 요청한 id에 해당하는 Task를 찾아 삭제하고 값을 리턴합니다.
     *
     * @param id 삭제하고자 하는 Task id
     * @return 삭제된 Task 값
     */
    public Task deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task);

        return task;
    }
}
