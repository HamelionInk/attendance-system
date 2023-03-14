package com.codeinside.attendancesystem.exception;

public class AdminNotFoundException extends RuntimeException {

    public AdminNotFoundException() {
        super();
    }

    public AdminNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AdminNotFoundException(final String message) {
        super(message);
    }

    public AdminNotFoundException(final Throwable cause) {
        super(cause);
    }
}
