package com.evdictionaries.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subjects extends BaseEntity {
    private String code;
    private String name;
    private String description;

    public Subjects(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Subjects() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
