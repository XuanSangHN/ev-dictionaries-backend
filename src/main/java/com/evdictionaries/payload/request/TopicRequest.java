package com.evdictionaries.payload.request;

import org.springframework.web.multipart.MultipartFile;

public class TopicRequest extends AbstractRequest<TopicRequest> {
    private String name;
    private MultipartFile image;
    private String description;

    public TopicRequest() {
    }

    public TopicRequest(String name, MultipartFile image, String description) {
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
