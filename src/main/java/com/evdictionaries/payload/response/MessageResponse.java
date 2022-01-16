package com.evdictionaries.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageResponse {
    @JsonProperty("Timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    @JsonProperty("Code")
    private Long code;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Message")
    private String message;

    public MessageResponse(Long code, String status, String message) {
        this.timestamp = new Date();
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
