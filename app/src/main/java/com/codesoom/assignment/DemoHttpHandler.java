// TODO
// 1. Read - collection (완)
// 2. Read - item/element => 완료
// 3. Create (완)
// 4. Update => 완료
// 5. Delete => 완료

package com.codesoom.assignment;

import com.codesoom.assignment.models.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DemoHttpHandler implements HttpHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Task> tasks = new ArrayList<Task>();
    private Long newId = 0L;

    public DemoHttpHandler() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Do nothing");
        tasks.add(task);
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // REST - CRUD
        // 1. Method - GET, POST, PUT/PATCH, DELETE, ...
        // 2. Path - "/", "/tasks", "/tasks/1", ...
        // 3. Headers, Body(Content)

        String method = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();

        System.out.println(method + " " + path);

        if (path.equals("/tasks")) {
            handleCollection(exchange, method);
            return;
        }

        if (path.startsWith("/tasks/")) {
            Long id = Long.parseLong(path.substring("/tasks/".length()));
            handleItem(exchange, method, id);
            return;
        }

        send(exchange, 200, "REST API practice...");
    }


    private void send(HttpExchange exchange, int statusCode, String content) throws IOException {
        // http status code, content 길이 반환
        exchange.sendResponseHeaders(statusCode, content.getBytes().length);

        OutputStream outputStream = exchange.getResponseBody();
        // write 메서드가 byte[]를 받으므로, content 넘겨줄 때 getBytes 메소드 사용.
        outputStream.write(content.getBytes());

        outputStream.flush();
        outputStream.close();
    }

    private void handleCollection(HttpExchange exchange, String method)
            throws IOException {
        if (method.equals("GET")) {
            handleList(exchange);
        }
        if (method.equals("POST")) {
            handleCreate(exchange);
        }
    }

    private void handleItem(HttpExchange exchange, String method, Long id)
            throws IOException {
        Task task = findTask(id);

        if (task == null) {
            send(exchange, 404, "");
            return;
        }

        if (method.equals("GET")) {
            handleDetail(exchange);
        }

        if (method.equals("PUT") || method.equals("PATCH")) {
            handleUpdate(exchange, task);
        }

        if (method.equals("DELETE")) {
            handleDelete(exchange, task);
        }
    }


    private void handleList(HttpExchange exchange) throws IOException {
        send(exchange, 200, tasktoJSON(tasks));
    }

    private void handleDetail(HttpExchange exchange) throws IOException {
        send(exchange, 200, tasktoJSON(tasks));
    }

    private void handleCreate(HttpExchange exchange) throws IOException {
        String body = getBody(exchange);

        Task task = toTask(body);
        task.setId(generateId());
        tasks.add(task);

        send(exchange, 201, tasktoJSON(task));
    }

    private void handleUpdate(HttpExchange exchange, Task task) throws IOException {
        String body = getBody(exchange);
        Task source = toTask(body);

        task.setTitle(source.getTitle());
        send(exchange, 200, tasktoJSON(tasks));
    }

    private void handleDelete(HttpExchange exchange, Task task) throws IOException {
        tasks.remove(task);

        send(exchange, 200,"");
    }


    private String getBody(HttpExchange exchange) {
        InputStream inputStream = exchange.getRequestBody();
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    private Task toTask(String content) throws JsonProcessingException {
        return objectMapper.readValue(content, Task.class);
    }

    private String tasktoJSON(Object object) throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        objectMapper.writeValue(outputStream, object);

        return outputStream.toString();
    }

    private String toJSON() throws IOException {
        return tasktoJSON(tasks);
    }

    private Task findTask(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private Long generateId() {
        newId += 1;
        return newId;
    }
}
