package com.epam.esm.controller;

import org.springframework.http.HttpStatus;

public class ControllerException extends RuntimeException {

    private int errorCode;
    private HttpStatus status;

    public ControllerException(String message, int errorCode, HttpStatus status) {
        this(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public ControllerException() {
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Exception exception) {
        super(exception);
    }

    public ControllerException(String message, Exception exception) {
        super(message, exception);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

}