package com.evdictionaries.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubjectsResponse {
    @JsonProperty("Subjects_Id")
    private long id;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Id_Class")
    private long id_class;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getId_class() {
        return id_class;
    }

    public void setId_class(long id_class) {
        this.id_class = id_class;
    }

    public SubjectsResponse(long id, String code, String name, String description, long id_class) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.id_class = id_class;
    }
}
