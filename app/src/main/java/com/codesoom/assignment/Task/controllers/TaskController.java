package com.codesoom.assignment.Task.controllers;

import com.codesoom.assignment.Task.application.TaskService;
import com.codesoom.assignment.Task.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Task REST API 컨트롤러
 */
@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {
    private TaskService taskService;

    /**
     * TaskController 생성자
     * @param taskService 생성자에 TaskService 의존성 주입
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * 전체 Task 를 리턴합니다.
     * @return
     */
    @GetMapping
    public List<Task> list() {
        return taskService.getTasks();
    }

    /**
     * 요청한 id 의 Task 를 리턴합니다.
     * @param id 요청 id
     * @return 요청한 Task
     */
    @GetMapping("{id}")
    public Task detail(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    /**
     * 새로운 Task 를 생성합니다.
     * @param task task 객체
     * @return 새로운 Task
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    /**
     * 기존 Task 의 정보를 수정합니다.
     * @param id  해당 task 자원 번호
     * @param task task 객체의 정보
     * @return 업데이트된 task
     */
    @PutMapping("{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    /**
     * 기존 Task 의 정보를 수정합니다.
     * @param id  해당 task 자원 번호
     * @param task task 객체의 정보
     * @return 업데이트된 task
     */
    @PatchMapping("{id}")
    public Task patch(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    /**
     * 요청한 id의 Task 를 삭제합니다.
     * @param id 해당 task 자원 번호
     * @return 삭제된 Task
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Task delete(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }
}
