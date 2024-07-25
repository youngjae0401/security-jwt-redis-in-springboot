package com.shop.java_app.common.exception;

import com.shop.java_app.common.dto.ResponseDto;
import io.jsonwebtoken.JwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseDto<?> handleNotFoundException(NotFoundException exception) {
        return ResponseDto.fail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DuplicationException.class)
    public ResponseDto<?> handleDuplicationException(DuplicationException exception) {
        return ResponseDto.fail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseDto<?> handleJwtException(JwtException exception) {
        return ResponseDto.fail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseDto<?> handleBindException(BindException exception) {
        final List<String> errorMessages = exception.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseDto.fail(HttpStatus.BAD_REQUEST, errorMessages);
    }
}
