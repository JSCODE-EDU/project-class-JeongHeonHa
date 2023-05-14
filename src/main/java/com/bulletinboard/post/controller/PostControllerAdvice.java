package com.bulletinboard.post.controller;

import com.bulletinboard.post.exception.InvalidPostException;
import com.bulletinboard.post.exception.PostNotFoundException;
import com.bulletinboard.utils.ApiUtils.ApiResult;
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

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResult<?>> handlePostNotFoundException(PostNotFoundException e) {
        log.info("PostNotFoundException = {}", e.getMessage());

        return error(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPostException.class)
    public ResponseEntity<ApiResult<?>> handleInvalidPostException(InvalidPostException e) {
        log.info("InvalidPostException = {}", e.getMessage());

        return error(e, HttpStatus.BAD_REQUEST);
    }
}
