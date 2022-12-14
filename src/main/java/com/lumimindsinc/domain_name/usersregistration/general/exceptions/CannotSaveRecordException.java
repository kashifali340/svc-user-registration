package com.lumimindsinc.domain_name.usersregistration.general.exceptions;

public class CannotSaveRecordException extends Exception {

    public CannotSaveRecordException() {
    }

    public CannotSaveRecordException(String message) {
        super(message);
    }

    public CannotSaveRecordException(String message, Throwable cause) {
        super(message, cause);
    }

}
