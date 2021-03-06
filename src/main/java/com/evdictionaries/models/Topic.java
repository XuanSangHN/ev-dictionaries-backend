package com.evdictionaries.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "topic")
public class Topic extends BaseEntity{
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Image_Url")
    private String image;
    @JsonProperty("Description")
    private String description;

    public Topic() {
    }

    public Topic(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
