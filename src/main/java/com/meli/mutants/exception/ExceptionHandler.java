package com.meli.mutants.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Log4j2
@RestControllerAdvice
class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    ResponseEntity<Object> handleGeneralException(Exception e) {
        log.error(e, e.getCause());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {DnaException.class})
    ResponseEntity<Object> handleDnaException(DnaException e) {
        log.error("Error analysing Dna: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

