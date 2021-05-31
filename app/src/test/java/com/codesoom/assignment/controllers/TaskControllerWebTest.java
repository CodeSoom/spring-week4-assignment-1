package com.codesoom.assignment.controllers;

import com.codesoom.assignment.Task.TaskNotFoundException;
import com.codesoom.assignment.Task.application.TaskService;
import com.codesoom.assignment.Task.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * WebMvcTest: Testing the Controller
 * 이전 단계에서 Service layer 에 대한 Test 를 진행했다면, WebMvcTest 는 controller 를 위한 test
 */
@WebMvcTest
public class TaskControllerWebTest {
    @Autowired
    private MockMvc mockMvc; // 우리가 new 하지 않고, 스프링이 알아서 자동으로 넣어준다 => autowired

    @MockBean  // 가짜 객체를 사용 -> from mokito.MockBean
    private TaskService taskService;

    List<Task> tasks; // 선언만 공통으로 빼고, 정의는 메소드 내부에서 해준다.

    @BeforeEach
    void SetUp() {
        tasks = new ArrayList<>();
        Task task = new Task();
        task.setTitle("Test Task");
        task.setId(1L);
        tasks.add(task);

        // mocking
        given(taskService.getTasks()).willReturn(tasks);
        given(taskService.getTask(1L)).willReturn(task);
        given(taskService.getTask(100L))
                .willThrow(new TaskNotFoundException(100L));
    }

    @Test
    public void list() throws Exception {
//        List<Task> tasks = new ArrayList<>();
//        Task task = new Task();
//        task.setTitle("Test Task");
//        tasks.add(task);
        given(taskService.getTasks()).willReturn(tasks);
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Task")));
    }

    @Test
    void detailWithValidId() throws Exception {
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Task")));
    }

    @Test
    void detailWithInvalidId() throws Exception {
        mockMvc.perform(get("/tasks/100")).andExpect(status().isNotFound());
    }

    // 이런식으로 테스트 시나리오를 만들어 볼 것.
    @Test
    void testTogether() throws Exception {

//        mockMvc.perform(get("/tasks"))
//                .andExpect(status().isOk());

//        mockMvc.perform(get("/tasks/1"))
//                .andExpect(status().isOk());

        mockMvc.perform(post("/tasks"))
                .andExpect(status().isOk());

    }
}
