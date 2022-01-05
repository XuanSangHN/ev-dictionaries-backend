package com.evdictionaries.payload.response;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassResponse {
    private List<ObjectClassReponse> Data = new ArrayList<>();
    private int Code;
    private String Message;

    public List<ObjectClassReponse> getData() {
        return Data;
    }

    public void setData(List<ObjectClassReponse> data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
