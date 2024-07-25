package com.shop.java_app.common.dto;

import org.springframework.http.HttpStatus;

public enum ResponseStatus {
    OK(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase()),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    private final int code;
    private final String message;

    ResponseStatus(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
