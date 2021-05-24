package com.codesoom.assignment.domain;


// Task를 하나의 Entity로 본다.
// 동일성을 알기 위해 하나의 identifier 를 사용 (unique key)

import javax.persistence.Entity;
import javax.persistence.Id;

// 1. Entity (Domain 관점에서)
// 2. RDB의 Entity와 다름에 주의
//  -> Jpa Entity 역할도 같이 함
@Entity
public class Task {

    // Identifier - Unique key
     @Id
//     @GeneratedValue
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
