package com.example.oril_test_task.persistence.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "currencies")
public class Currency extends BaseEntity {

    @Indexed(unique = true)
    @Field
    private String code;

    public Currency() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
