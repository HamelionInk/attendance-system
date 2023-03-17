package com.codeinside.attendancesystem.exception;

public class DateMatchesException extends RuntimeException {

    public DateMatchesException() {
        super();
    }

    public DateMatchesException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DateMatchesException(final String message) {
        super(message);
    }

    public DateMatchesException(final Throwable cause) {
        super(cause);
    }
}
