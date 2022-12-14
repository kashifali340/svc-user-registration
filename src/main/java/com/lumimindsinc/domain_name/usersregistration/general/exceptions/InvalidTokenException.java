package com.lumimindsinc.domain_name.usersregistration.general.exceptions;

public class InvalidTokenException extends Exception {


    public InvalidTokenException() {
    }

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }


}
