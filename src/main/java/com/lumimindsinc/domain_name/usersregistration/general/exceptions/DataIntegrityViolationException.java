package com.lumimindsinc.domain_name.usersregistration.general.exceptions;

public class DataIntegrityViolationException extends Exception {

    public DataIntegrityViolationException() {
    }

    public DataIntegrityViolationException(String message) {
        super(message);
    }

    public DataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }

}
