package com.century21.century21cambodia.exception;

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
