package com.evdictionaries.payload.request;

public class SubjectsRequest extends AbstractRequest<SubjectsRequest> {
    private String code;
    private String name;
    private String description;
    private long id_class;

    public SubjectsRequest() {
    }

    public SubjectsRequest(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public SubjectsRequest(String code, String name, String description, long id_class) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.id_class = id_class;
    }

    public long getId_class() {
        return id_class;
    }

    public void setId_class(long id_class) {
        this.id_class = id_class;
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
