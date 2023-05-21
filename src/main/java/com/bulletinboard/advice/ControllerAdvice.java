package com.bulletinboard.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(BindingResult bindingResult) {
        String defaultMessage = bindingResult.getAllErrors()
                        .get(0).getDefaultMessage();

        log.error("MethodArgumentNotValidException = {}", defaultMessage);

        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .errorCode(ErrorCode.BAD_REQUEST)
                .message(defaultMessage)
                .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        log.error("BadRequestException = {}", e.getMessage());

        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .errorCode(ErrorCode.BAD_REQUEST)
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        log.error("NotFoundException = {}", e.getMessage());

        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .errorCode(ErrorCode.NOT_FOUND)
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException = {}", e.getMessage());

        return ResponseEntity.internalServerError().body(ErrorResponse.builder()
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build());
    }
}
