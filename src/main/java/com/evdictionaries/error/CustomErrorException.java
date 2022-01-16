package com.evdictionaries.error;

import org.springframework.http.HttpStatus;

public class CustomErrorException extends RuntimeException {
    private HttpStatus Status = null;
    private Object Data = null;

    public CustomErrorException() {
        super();
    }

    public CustomErrorException(String message) {
        super(message);
    }

    public CustomErrorException(HttpStatus status, String message) {
        this(message);
        this.Status = status;
    }

    public CustomErrorException(HttpStatus status, String message, Object data) {
        this(status, message);
        this.Data = data;
    }

    public HttpStatus getStatus() {
        return Status;
    }

    public void setStatus(HttpStatus status) {
        this.Status = status;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        this.Data = data;
    }
}
