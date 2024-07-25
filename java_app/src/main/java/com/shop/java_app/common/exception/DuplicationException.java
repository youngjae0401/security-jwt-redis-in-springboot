package com.shop.java_app.common.exception;

public class DuplicationException extends RuntimeException {
    public DuplicationException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
