package com.codesoom.assignment.models;

import com.codesoom.assignment.TaskNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


public interface TaskRepository {

    List<Task> findAll();

    Task find(Long id);

    Task save(Task task);

    Task remove(Task task);

}
