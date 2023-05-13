package com.bulletinboard.advice;

import com.bulletinboard.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.bulletinboard.utils.ApiUtils.error;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    private ResponseEntity<ApiUtils.ApiResponse<?>> newResponse(Throwable throwable, HttpStatus status) {
        return newResponse(throwable.getMessage(), status);
    }

    private ResponseEntity<ApiUtils.ApiResponse<?>> newResponse(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return new ResponseEntity<>(error(message, status), headers, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiUtils.ApiResponse<?>> handleMethodArgumentNotValidException(BindingResult bindingResult) {
        String defaultMessage = bindingResult.getFieldError()
                .getDefaultMessage();
        log.info("MethodArgumentNotValidException = {}", defaultMessage);

        return newResponse(defaultMessage, HttpStatus.BAD_REQUEST);
    }
}
