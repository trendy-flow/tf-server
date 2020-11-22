package com.trendyflow.authserver.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private ApiError apiError;

    protected BaseException(ApiError apiError) {
        this.apiError = apiError;
    }
}
