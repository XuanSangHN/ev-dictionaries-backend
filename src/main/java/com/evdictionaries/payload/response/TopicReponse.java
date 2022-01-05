package com.evdictionaries.payload.response;

import com.evdictionaries.models.Topic;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TopicReponse {
    private int PageNo;
    private int PageSize;
    private int Code;
    private String Message;
    private List<Topic> Data = new ArrayList<>();

    public int getPageNo() {
        return PageNo;
    }

    public void setPageNo(int pageNo) {
        PageNo = pageNo;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
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

    public List<Topic> getData() {
        return Data;
    }

    public void setData(List<Topic> data) {
        Data = data;
    }
}
