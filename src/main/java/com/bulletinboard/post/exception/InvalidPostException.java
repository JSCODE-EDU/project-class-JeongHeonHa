package com.bulletinboard.post.exception;

public class InvalidPostException extends RuntimeException {

    private static final String MESSAGE = "게시물은 최대 100개만 가능합니다.";

    public InvalidPostException() {
        super(MESSAGE);
    }

    public InvalidPostException(String message) {
        super(message);
    }
}
