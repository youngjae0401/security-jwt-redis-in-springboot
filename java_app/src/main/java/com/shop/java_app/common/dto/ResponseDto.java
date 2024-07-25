package com.shop.java_app.common.dto;

import org.springframework.http.HttpStatus;

public class ResponseDto<T> {
    private final int code;
    private final String message;
    private final T data;

    private ResponseDto(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseDto<T> success() {
        return new ResponseDto<>(ResponseStatus.OK.getCode(), ResponseStatus.OK.getMessage(), null);
    }

    public static <T> ResponseDto<T> success(final T data) {
        return new ResponseDto<>(ResponseStatus.OK.getCode(), ResponseStatus.OK.getMessage(), data);
    }

    public static <T> ResponseDto<T> success(final HttpStatus httpStatus, final T data) {
        return new ResponseDto<>(httpStatus.value(), httpStatus.getReasonPhrase(), data);
    }

    public static <T> ResponseDto<T> success(final HttpStatus httpStatus, final String message, final T data) {
        return new ResponseDto<>(httpStatus.value(), message, data);
    }

    public static <T> ResponseDto<T> fail() {
        return new ResponseDto<>(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST.getMessage(), null);
    }

    public static <T> ResponseDto<T> fail(final T data) {
        return new ResponseDto<>(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST.getMessage(), data);
    }

    public static <T> ResponseDto<T> fail(final String message) {
        return new ResponseDto<>(ResponseStatus.BAD_REQUEST.getCode(), message, null);
    }

    public static <T> ResponseDto<T> fail(final HttpStatus httpStatus) {
        return new ResponseDto<>(httpStatus.value(), httpStatus.getReasonPhrase(), null);
    }

    public static <T> ResponseDto<T> fail(final HttpStatus httpStatus, final String message) {
        return new ResponseDto<>(httpStatus.value(), message, null);
    }

    public static <T> ResponseDto<T> fail(final HttpStatus httpStatus, final T data) {
        return new ResponseDto<>(httpStatus.value(), httpStatus.getReasonPhrase(), data);
    }

    public static <T> ResponseDto<T> fail(final HttpStatus httpStatus, final String message, final T data) {
        return new ResponseDto<>(httpStatus.value(), message, data);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
