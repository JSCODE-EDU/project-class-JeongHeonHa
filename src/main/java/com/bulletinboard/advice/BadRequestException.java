package com.bulletinboard.advice;

public class BadRequestException extends BusinessException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
