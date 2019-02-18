package com.century21.exception;

public class CustomRuntimeException extends RuntimeException {
    private int statusCode;
    public CustomRuntimeException(int statusCode, String status) {
        super(status);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
