```java
    public Task updateTask(Long id, Task source) {
//        Task task = getTask(id); // 상위의 메소드에 의존하지 않고, repository에 의존하도록 바꾼다.
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));;
        task.setTitle(source.getTitle());

        return task;
    }

```


- @Transactional
- @GenerateId

- :8080/h2-console
