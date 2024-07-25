package com.shop.java_app.common.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
