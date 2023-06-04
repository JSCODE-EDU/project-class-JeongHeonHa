package com.bulletinboard.member.exception;

import com.bulletinboard.advice.BadRequestException;

public class InvalidMemberPasswordException extends BadRequestException {

    public InvalidMemberPasswordException() {
    }

    public InvalidMemberPasswordException(String message) {
        super(message);
    }
}
