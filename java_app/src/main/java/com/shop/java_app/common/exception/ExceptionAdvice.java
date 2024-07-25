package com.shop.java_app.common.exception;

import com.shop.java_app.common.dto.ResponseDto;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseDto<?> handleNotFoundException(NotFoundException exception) {
        return ResponseDto.fail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseDto<?> handleJwtException(JwtException exception) {
        return ResponseDto.fail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }
}
