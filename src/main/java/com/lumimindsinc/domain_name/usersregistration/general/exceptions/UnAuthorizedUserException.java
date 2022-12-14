package com.lumimindsinc.domain_name.usersregistration.general.exceptions;


public class UnAuthorizedUserException extends Exception {

    public UnAuthorizedUserException() {
    }
    public UnAuthorizedUserException(String message) {
        super(message);
    }

    public UnAuthorizedUserException(String message, Throwable cause) {
        super(message, cause);
    }

}
