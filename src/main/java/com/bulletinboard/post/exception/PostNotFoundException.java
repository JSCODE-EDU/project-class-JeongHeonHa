package com.bulletinboard.post.exception;

import com.bulletinboard.advice.NotFoundException;

public class PostNotFoundException extends NotFoundException {

    private static final String MESSAGE = "게시물을 찾을 수 없습니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
