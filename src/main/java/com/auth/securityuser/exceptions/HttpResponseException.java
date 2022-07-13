package com.auth.securityuser.exceptions;

import org.springframework.http.HttpStatus;

public class HttpResponseException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public HttpResponseException(HttpStatus httpStatus, String message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
