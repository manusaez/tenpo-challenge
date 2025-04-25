package com.tenpo.challenge.exception;

public class MaxRetriesException extends RuntimeException {

    public MaxRetriesException(String message) {
        super(message);
    }
}
