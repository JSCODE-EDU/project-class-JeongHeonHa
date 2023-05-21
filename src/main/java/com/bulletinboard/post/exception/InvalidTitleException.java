package com.bulletinboard.post.exception;

import com.bulletinboard.advice.BadRequestException;

public class InvalidTitleException extends BadRequestException {

    private static final String MESSAGE = "제목은 1글자 이상 15글자 이하여야 합니다.";

    public InvalidTitleException() {
        super(MESSAGE);
    }
}
