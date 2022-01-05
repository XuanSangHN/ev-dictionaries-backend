package com.evdictionaries.payload.response;

import com.evdictionaries.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountResponse {
    private List<User> Data = new ArrayList<>();
    private int Code;
    private String Message;

    public List<User> getData() {
        return Data;
    }

    public void setData(List<User> data) {
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
