package com.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDTO {
    @JsonProperty("error_code")
    private int code;

    @JsonProperty("error_msg")
    private String message;

    public int getCode() {
        return code;
    }

    public ErrorDTO setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorDTO setMessage(String message) {
        this.message = message;
        return this;
    }
}
