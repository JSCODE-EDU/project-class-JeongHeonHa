package com.bulletinboard.advice;

public class NotFoundException extends BusinessException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
