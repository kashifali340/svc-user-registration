package com.lumimindsinc.domain_name.usersregistration.general.exceptions;

public class InvalidUserDataException extends Exception {

    public InvalidUserDataException() {
    }

    public InvalidUserDataException(String message) {
        super(message);
    }

    public InvalidUserDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
