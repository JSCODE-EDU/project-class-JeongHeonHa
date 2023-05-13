package com.bulletinboard.post.controller;

import com.bulletinboard.post.exception.InvalidPostException;
import com.bulletinboard.post.exception.PostNotFoundException;
import com.bulletinboard.utils.ApiUtils;
import com.bulletinboard.utils.ApiUtils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.bulletinboard.utils.ApiUtils.error;

@Slf4j
@RestControllerAdvice
public class PostControllerAdvice {

    private ResponseEntity<ApiUtils.ApiResponse<?>> newResponse(Throwable throwable, HttpStatus status) {
        return newResponse(throwable.getMessage(), status);
    }

    private ResponseEntity<ApiUtils.ApiResponse<?>> newResponse(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return new ResponseEntity<>(error(message, status), headers, status);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handlePostNotFoundException(PostNotFoundException e) {
        log.info("PostNotFoundException = {}", e.getMessage());

        return newResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPostException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidPostException(InvalidPostException e) {
        log.info("InvalidPostException = {}", e.getMessage());

        return newResponse(e, HttpStatus.BAD_REQUEST);
    }
}
