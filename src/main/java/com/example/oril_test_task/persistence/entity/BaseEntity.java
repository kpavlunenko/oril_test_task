package com.example.oril_test_task.persistence.entity;

import org.springframework.data.annotation.Id;

public abstract class BaseEntity {

    @Id
    private Long id;

    public BaseEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
