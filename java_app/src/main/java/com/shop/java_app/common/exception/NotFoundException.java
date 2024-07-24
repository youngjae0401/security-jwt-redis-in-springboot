package com.shop.java_app.common.exception;

public class NotFoundException extends BaseException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
