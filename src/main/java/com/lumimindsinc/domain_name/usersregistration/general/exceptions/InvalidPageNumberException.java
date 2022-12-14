package com.lumimindsinc.domain_name.usersregistration.general.exceptions;

public class InvalidPageNumberException extends Exception {


        public InvalidPageNumberException() {
        }

        public InvalidPageNumberException(String message) {
            super(message);
        }

        public InvalidPageNumberException(String message, Throwable cause) {
            super(message, cause);
        }


    }
