package com.bulletinboard.advice;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String error;
    private final String message;

    @Builder
    public ErrorResponse(ErrorCode errorCode, String message) {
        this.statusCode = errorCode.getHttpStatus().value();
        this.error = errorCode.getHttpStatus().name();
        this.message = getMessage(errorCode, message);
    }

    public String getMessage(ErrorCode errorCode, String message) {
        if (StringUtils.hasText(message)) return message;
        return errorCode.getMessage();
    }
}
