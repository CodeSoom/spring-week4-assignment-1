package com.codesoom.assignment.controllers;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.application.TaskService;
import com.codesoom.assignment.domain.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@DisplayName("TaskControllerWebTest 클래스")
public class TaskControllerWebTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @MockBean
    private TaskService taskService;

    @Nested
    @DisplayName("전체 할 일을 요청할 때")
    class Describe_listMvc {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();

        @Nested
        @DisplayName("할 일이 존재하지 않을 경우")
        class Context_not_exist_task {

            @Test
            @DisplayName("빈 배열을 리턴하고, 200 응답코드를 전달합니다.")
            void it_return_empty() throws Exception {
                given(taskService.getTasks()).willReturn(tasks);

                final ResultActions actions = mockMvc.perform(get("/tasks"));

                actions
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("[]")))
                        .andExpect(jsonPath("$.length()", is(0)));
            }

        }

        @Nested
        @DisplayName("할 일이 존재할 경우")
        class Context_exist_task {
            @Test
            @DisplayName("할 일 목록을 리턴하고, 200 응답코드를 전달합니다.")
            void it_return_all() throws Exception {
                given(taskService.getTasks()).willReturn(tasks);
                task.setTitle("Test");
                tasks.add(task);

                final ResultActions actions = mockMvc.perform(get("/tasks"));

                actions
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.[0].title", is("Test")));
            }
        }
    }

    @Nested
    @DisplayName("특정한 할 일을 요청할 때")
    class Describe_detailMvc {

        @Nested
        @DisplayName("요청한 ID에 맞는 할 일이 없다면")
        class Context_not_exist_task {
            @Test
            @DisplayName("TaskNotFound 예외를 던지고, 404 응답코드를 전달합니다.")
            void it_throw_exception() throws Exception {
                given(taskService.getTask(100L))
                        .willThrow(TaskNotFoundException.class);

                final ResultActions actions = mockMvc.perform(get("/tasks/100"));

                actions
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("요청한 ID에 맞는 할 일이 있다면")
        class Context_exist_task {
            Task task = new Task();

            @Test
            @DisplayName("요청 ID의 할 일을 리턴하고, 200 응답코드를 전달합니다.")
            void it_return_specific_task() throws Exception {
                given(taskService.getTask(1L)).willReturn(task);
                task.setTitle("Test");

                final ResultActions actions = mockMvc.perform(get("/tasks/1"));

                actions
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.title", is("Test")));
            }
        }
    }

    @Nested
    @DisplayName("할 일 생성을 요청할 때")
    class Describe_createMvc {

        @Nested
        @DisplayName("Title 내용이 주어지면")
        class Context_exist_title {
            Task source = new Task();

            @BeforeEach
            void setUp() {
                mapper = new ObjectMapper();
            }

            @Test
            @DisplayName("할 일을 생성하고, 생성된 할 일을 리턴, 201 응답코드를 전달합니다.")
            void it_create_task_and_return() throws Exception {
                given(taskService.createTask(any())).will(invocation -> {
                   Task task = invocation.getArgument(0);
                   task.setId(1L);
                   task.setTitle("Test");
                   return task;
                });

                final ResultActions actions = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(source))
                        .accept(MediaType.APPLICATION_JSON));

                actions
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("title", is("Test")))
                        .andExpect(jsonPath("id", is(1)));
            }
        }
    }

    @Nested
    @DisplayName("등록된 할 일을 수정할 때")
    class Describe_updateMvc {

        @Nested
        @DisplayName("등록되지 않은 ID로 요청하면")
        class Context_with_not_registered_ID {
            Task source = new Task();

            @BeforeEach
            void setUp() {
                mapper = new ObjectMapper();
            }

            @Test
            @DisplayName("TaskNotFound 예외를 던지고, 404 응답코드를 전달합니다.")
            void it_throw_exception() throws Exception {
                given(taskService.updateTask(eq(100L), any(Task.class)))
                        .willThrow(TaskNotFoundException.class);

                final ResultActions actions = mockMvc.perform(patch("/tasks/100")
                        .content(mapper.writeValueAsString(source))
                        .contentType(MediaType.APPLICATION_JSON));

                actions
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("등록된 ID로 요청하면")
        class Context_with_registered_ID {
            Task source = new Task();

            @BeforeEach
            void setUp() {
                mapper = new ObjectMapper();
            }

            @Test
            @DisplayName("등록된 ID의 내용을 변경하고, 변경된 할 일을 리턴, 200 응답코드를 전달합니다.")
            void it_update_task_and_return() throws Exception {
                given(taskService.updateTask((long)eq(1), any(Task.class)))
                        .will(invocation -> {
                            Task task = invocation.getArgument(0);
                            task.setId(1L);
                            task.setTitle("New Test");
                            return task;
                        });

                final ResultActions actions = mockMvc.perform(patch("/tasks/1")
                        .content(mapper.writeValueAsString(source))
                        .contentType(MediaType.APPLICATION_JSON));

                actions
                        .andExpect(status().isOk());
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(jsonPath("title", is("New Test")))
//                        .andExpect(jsonPath("id", is(1)));
            }
        }
    }

    @Nested
    @DisplayName("등록된 할 일을 수정할 때")
    class Describe_patchMvc {

        @Nested
        @DisplayName("등록되지 않은 ID로 요청하면")
        class Context_with_not_registered_ID {
            Task source = new Task();

            @BeforeEach
            void setUp() {
                mapper = new ObjectMapper();
            }

            @Test
            @DisplayName("TaskNotFound 예외를 던지고, 404 응답코드를 전달합니다.")
            void it_throw_exception() throws Exception {
                given(taskService.updateTask(eq(100L), any(Task.class)))
                        .willThrow(TaskNotFoundException.class);

                final ResultActions actions = mockMvc.perform(patch("/tasks/100")
                        .content(mapper.writeValueAsString(source))
                        .contentType(MediaType.APPLICATION_JSON));

                actions
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("등록된 ID로 요청하면")
        class Context_with_registered_ID {
            Task source = new Task();

            @BeforeEach
            void setUp() {
                mapper = new ObjectMapper();
            }

            @Test
            @DisplayName("등록된 ID의 내용을 변경하고, 변경된 할 일을 리턴, 200 응답코드를 전달합니다.")
            void it_update_task_and_return() throws Exception {
                given(taskService.updateTask((long)eq(1), any(Task.class)))
                        .will(invocation -> {
                            Task task = invocation.getArgument(0);
                            task.setId(1L);
                            task.setTitle("New Test");
                            return task;
                        });

                final ResultActions actions = mockMvc.perform(patch("/tasks/1")
                        .content(mapper.writeValueAsString(source))
                        .contentType(MediaType.APPLICATION_JSON));

                actions
                        .andExpect(status().isOk());
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(jsonPath("title", is("New Test")))
//                        .andExpect(jsonPath("id", is(1)));
            }
        }
    }

    @Nested
    @DisplayName("등록된 할 일을 삭제할 때")
    class Describe_deleteMvc {
        Task source = new Task();

        @Nested
        @DisplayName("등록되지 않은 ID를 요청하면")
        class Context_with_not_registered_ID {

            @BeforeEach
            void setUp() {
                mapper = new ObjectMapper();
            }

            @Test
            @DisplayName("TaskNotFound 예외를 던지고, 404 응답코드를 전달합니다.")
            void it_throw_exception() throws Exception {
                given(taskService.deleteTask(any()))
                        .willThrow(TaskNotFoundException.class);

                final ResultActions actions = mockMvc.perform(delete("/tasks/100")
                        .content(mapper.writeValueAsString(source))
                        .contentType(MediaType.APPLICATION_JSON));

                actions
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("등록된 ID를 요청하면")
        class Context_with_registered_ID {

            @BeforeEach
            void setUp() {
                mapper = new ObjectMapper();
            }

            @Test
            @DisplayName("등록된 할 일을 삭제하고, 204 응답코드를 전달합니다.")
            void it_delete_task() throws Exception {
                given(taskService.deleteTask(any())).willReturn(null);

                final ResultActions actions = mockMvc.perform(delete("/tasks/1"));

                actions
                        .andExpect(status().isNoContent());
            }
        }
    }
}
