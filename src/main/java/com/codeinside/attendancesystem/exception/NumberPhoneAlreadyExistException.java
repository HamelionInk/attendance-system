package com.codeinside.attendancesystem.exception;

public class NumberPhoneAlreadyExistException extends RuntimeException {

    public NumberPhoneAlreadyExistException() {
        super();
    }

    public NumberPhoneAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NumberPhoneAlreadyExistException(final String message) {
        super(message);
    }

    public NumberPhoneAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
