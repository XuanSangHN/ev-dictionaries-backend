package com.evdictionaries.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ClassResponse {
    @JsonProperty("Id_Class")
    private Long id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("PageNo")
    private int pageNo;
    @JsonProperty("PageSize")
    private int paseSize;
    @JsonProperty("Subjects")
    private List<SubjectsResponse> subjects;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPaseSize() {
        return paseSize;
    }

    public void setPaseSize(int paseSize) {
        this.paseSize = paseSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubjectsResponse> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsResponse> subjects) {
        this.subjects = subjects;
    }

    public ClassResponse() {
    }
}
