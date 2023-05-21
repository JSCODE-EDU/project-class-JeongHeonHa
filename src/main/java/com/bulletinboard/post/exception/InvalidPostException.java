package com.bulletinboard.post.exception;

import com.bulletinboard.advice.BadRequestException;

public class InvalidPostException extends BadRequestException {

    private static final String MESSAGE = "게시물은 최대 100개만 가능합니다.";

    public InvalidPostException() {
        super(MESSAGE);
    }

    public InvalidPostException(String message) {
        super(message);
    }
}
