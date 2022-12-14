package com.lumimindsinc.domain_name.usersregistration.general.exceptions;

public class EmailNotFoundException extends Exception{

    public EmailNotFoundException() {
    }

    public EmailNotFoundException(String message) {
        super(message);
    }

    public EmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
