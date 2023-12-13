package com.consultant.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception exception) {
        log.error(exception.getMessage());
        Arrays.stream(exception.getStackTrace()).forEach(element -> log.error(element.toString()));
        return ExceptionResponse.toResponseEntity(ErrorConstant.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value =  CustomException.class)
    public ResponseEntity<Object> customExceptionHandler(CustomException customException) {
        log.trace(customException.getMessage());
        Arrays.stream(customException.getStackTrace()).forEach(element -> log.trace(element.toString()));
        return ExceptionResponse.toResponseEntity(customException.getErrorConstant());
    }

    static class ExceptionResponse extends ResponseEntity<Object> {

        private ExceptionResponse(ErrorConstant errorConstant) {
            super("message : " + errorConstant, errorConstant.getHttpStatus());
        }

        private static ResponseEntity<Object> toResponseEntity(ErrorConstant errorConstant) {
            return  new ExceptionResponse(errorConstant);
        }
    }
}
