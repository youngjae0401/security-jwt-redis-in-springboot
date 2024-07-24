package com.shop.java_app.common.exception;

public class BaseException extends RuntimeException {
    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    protected BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}
