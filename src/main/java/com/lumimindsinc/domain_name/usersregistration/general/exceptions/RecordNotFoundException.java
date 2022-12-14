package com.lumimindsinc.domain_name.usersregistration.general.exceptions;

public class RecordNotFoundException extends Exception {

    public RecordNotFoundException() {
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
