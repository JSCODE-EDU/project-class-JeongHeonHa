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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> handleMethodArgumentNotValidException(BindingResult bindingResult) {
        String defaultMessage = bindingResult.getFieldError()
                .getDefaultMessage();
        log.info("MethodArgumentNotValidException = {}", defaultMessage);

        return error(defaultMessage, HttpStatus.BAD_REQUEST);
    }
}
