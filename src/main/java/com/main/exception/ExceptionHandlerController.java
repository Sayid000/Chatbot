package com.main.exception;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class ExceptionHandlerController {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        log.error(e.getMessage(), e);
        String exception = e.getClass().getName().split("\\.")[e.getClass().getName().split("\\.").length - 1];
        return ResponseEntity.internalServerError().body(Collections.singletonMap("exceptionClass", exception));
    }
}
