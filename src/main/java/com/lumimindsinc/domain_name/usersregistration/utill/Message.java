package com.lumimindsinc.domain_name.usersregistration.utill;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
public class Message<T> {

    private String status;
    private String message;
    private int code;
    private T data;
    private Map<T,T> response;

    public Message() {

    }

    public Message(String status, String message, int code, T data) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public Message(String status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public Message(int code) {
        this.code = code;
    }

    public Message(String status, int code) {
        this.status = status;
        this.code = code;
    }


    public Message<T> setStatus(String status) {
        this.status = status;
        return this;
    }

    public Message<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Message<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Message<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage(){return this.message;}
    public String getMessage(String s, Object o, Locale locale) {
        return message = s + locale;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

}
