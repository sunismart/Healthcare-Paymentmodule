package com.Payment.PaymentIntegration.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private HttpStatus status; // HTTP status
    private String message;    // Response message
    private T data;            // Data returned with the response

    // Constructor
    public CommonResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
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
}
