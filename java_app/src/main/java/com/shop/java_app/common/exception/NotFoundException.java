package com.shop.java_app.common.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
