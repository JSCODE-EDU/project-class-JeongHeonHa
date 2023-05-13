package com.bulletinboard.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiUtils {

    public static<T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(true, response, null);
    }

    public static ApiResponse<?> error(Throwable throwable, HttpStatus status) {
        return new ApiResponse<>(false, null, new ApiError(throwable, status));
    }

    public static ApiResponse<?> error(String message, HttpStatus status) {
        return new ApiResponse<>(false, null, new ApiError(message, status));
    }

    @Getter
    public static class ApiError {
        private final String message;
        private final int status;

        ApiError(Throwable throwable, HttpStatus status) {
            this(throwable.getMessage(), status);
        }

        ApiError(String message, HttpStatus status) {
            this.message = message;
            this.status = status.value();
        }
    }

    @Getter
    public static class ApiResponse<T> {
        private final boolean success;
        private final T response;
        private final ApiError error;

        private ApiResponse(boolean success, T response, ApiError error) {
            this.success = success;
            this.response = response;
            this.error = error;
        }

        public boolean isSuccess() {
            return success;
        }

        public ApiError getError() {
            return error;
        }

        public T getResponse() {
            return response;
        }
    }
}
