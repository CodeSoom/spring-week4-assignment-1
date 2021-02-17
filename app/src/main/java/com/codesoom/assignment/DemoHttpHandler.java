package com.codesoom.assignment;


import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DemoHttpHandler implements HttpHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    private List<Product> products = new ArrayList<>();
    private Long newId = 0L;
    private Object InputStream;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();

        System.out.println(method + " " + path);

        if (path.equals("/products")) {
            handleCollection(exchange, method);
            return;
        }

        if (path.startsWith("/products")) {
            Long id = Long.parseLong(path.substring("/products/".length()));
            handleItem(exchange, method, id);
            return;
        }

        send(exchange, 200, "Hello, world!");
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
        Product product = findProduct(id);

        if (product == null) {
            send(exchange, 404, "");
            return;
        }

        if (method.equals("GET")) {
            handleDetail(exchange, product);
        }

        if (method.equals("PUT") || method.equals("PATCH")) {
            handleUpdate(exchange, product);
        }

        if (method.equals("DELETE")) {
            handleDelete(exchange, product);
        }
    }

    private void handleList(HttpExchange exchange) throws IOException {
        send(exchange, 200, toJSON(products));
    }

    private void handleDetail(HttpExchange exchange, Product product)
            throws IOException {
        send(exchange, 200, toJSON(product));
    }

    private void handleCreate(HttpExchange exchange) throws IOException {
        String body = getBody(exchange);

        Product product = toProduct(body);
        product.setId(generateId());
        products.add(product);

        send(exchange, 201, toJSON(product));
    }

    private void handleUpdate(HttpExchange exchange, Product product) throws IOException {
        String body = getBody(exchange);
        Product source = toProduct(body);

        product.setName(source.getName());

        send(exchange, 200, toJSON(product));
    }

    private void handleDelete(HttpExchange exchange, Product product) throws IOException {
        products.remove(product);

        send(exchange, 200, "");
    }

    private void send(HttpExchange exchange, int statusCode, String content)
            throws IOException{
        exchange.sendResponseHeaders(statusCode, content.getBytes().length);

        OutputStream outputStream = exchange.getResponseBody();

        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private String getBody(HttpExchange exchange){
        InputStream inputStream = exchange.getRequestBody();
        return new BufferedReader(new InputStreamReader((java.io.InputStream) InputStream))
                .lines()
                .collect(Collectors.joining("\n"));
    }


    private Product findProduct(Long id) {
        return  products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private Product toProduct(String content) throws JsonProcessingException {
        return objectMapper.readValue(content, Product.class);
    }

    private String toJSON(Object object) throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        objectMapper.writeValue(outputStream, object);

        return outputStream.toString();
    }

    private long generateId() {
        newId += 1;
        return newId;
    }
}
