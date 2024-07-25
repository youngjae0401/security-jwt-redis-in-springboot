package com.shop.java_app.common.exception;

public enum ErrorCode {
    INVALID_AUTHENTICATION_REQUEST("잘못된 인증 요청입니다."),
    INVALID_TOKEN("올바르지 않은 토큰입니다."),
    NOT_PERMISSION("권한이 없습니다."),
    NOT_FOUND("존재하지 않는 데이터입니다."),
    DUPLICATE_EMAIL("이미 존재하는 이메일입니다."),
    DUPLICATE_PHONE_NUMBER("이미 존재하는 휴대폰번호입니다.");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
