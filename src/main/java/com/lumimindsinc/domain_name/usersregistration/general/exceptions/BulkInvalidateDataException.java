package com.lumimindsinc.domain_name.usersregistration.general.exceptions;

public class BulkInvalidateDataException extends Exception {

    public BulkInvalidateDataException() {
    }

    public BulkInvalidateDataException(String message) {
        super(message);
    }

    public BulkInvalidateDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
