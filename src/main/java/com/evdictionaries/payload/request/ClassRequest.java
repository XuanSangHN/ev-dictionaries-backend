package com.evdictionaries.payload.request;

public class ClassRequest extends AbstractRequest<ClassRequest>{
    private String code;
    private String name;
    private String description;
    private long profile_id;

    public ClassRequest(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public long getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(long profile_id) {
        this.profile_id = profile_id;
    }

    public ClassRequest() {
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
