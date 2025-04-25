package com.tenpo.challenge.config;

import com.tenpo.challenge.dto.ErrorDto;
import com.tenpo.challenge.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(WebExchangeBindException ex) {
        log.error("An error occurred: ", ex);
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        ErrorDto errorDto = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .message(errors.toString())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = MaxRetriesException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(MaxRetriesException ex) {
        log.error("An error occurred: ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .errorCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = PercentageNotFoundException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(PercentageNotFoundException ex) {
        log.error("An error occurred: ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .errorCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MaxRateLimitException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(MaxRateLimitException ex) {
        log.error("An error occurred: ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .errorCode(HttpStatus.TOO_MANY_REQUESTS.value())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(ServerWebInputException ex) {
        log.error("An error occurred: ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> exceptionHandler(Exception ex) {
        log.error("An error occurred: ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
