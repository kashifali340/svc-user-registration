package com.lumimindsinc.domain_name.usersregistration.general.exceptions;

public class SamePreviousCurrentPasswordException extends Exception{
    public SamePreviousCurrentPasswordException() {
    }

    public SamePreviousCurrentPasswordException(String message) {
        super(message);
    }

    public SamePreviousCurrentPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

}
