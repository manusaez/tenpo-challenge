package com.tenpo.challenge.exception;

public class MaxRateLimitException extends RuntimeException {

    public MaxRateLimitException(String message) {
        super(message);
    }
}
