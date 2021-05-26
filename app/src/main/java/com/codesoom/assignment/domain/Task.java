package com.codesoom.assignment.domain;


// Entity (Domain)
// RDB의 Entity와 다름에 주의!
public class Task {
    // Identifier - Unique Key
    private Long id;

    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
