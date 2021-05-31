package com.codesoom.assignment.task;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task not found: " + id);
    }
}
