package com.meli.mutants.exception;

public class MutantServiceException extends RuntimeException {
    public MutantServiceException(String message) {
        super(message);
    }

    public MutantServiceException(String message, Throwable e) {
        super(message, e);
    }
}

