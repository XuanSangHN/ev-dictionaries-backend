package com.evdictionaries.payload.response;

import com.evdictionaries.models.Subjects;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectClassReponse {
    private int id;
    private String name;
    private String code;
    private String image;
    private String description;
    private List<Subjects> words = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Subjects> getWords() {
        return words;
    }

    public void setWords(List<Subjects> words) {
        this.words = words;
    }
}
