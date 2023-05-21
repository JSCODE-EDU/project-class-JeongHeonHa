package com.bulletinboard.member.exception;

import com.bulletinboard.advice.BadRequestException;

public class InvalidMemberEmailException extends BadRequestException {

    public InvalidMemberEmailException() {
    }

    public InvalidMemberEmailException(String message) {
        super(message);
    }
}
