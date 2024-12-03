package com.setupproject.setup.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Author Dibakar Chaudhary
 * 03/12/2024
 **/

public class Response<T> {
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
               "code='" + code + '\'' +
               ", message='" + message + '\'' +
               ", data=" + data +
               '}';
    }
}
