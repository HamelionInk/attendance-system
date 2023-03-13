package com.codeinside.attendancesystem.exception;

public class OutOfRangeAgeException extends RuntimeException {

    public OutOfRangeAgeException() {
        super();
    }

    public OutOfRangeAgeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OutOfRangeAgeException(final String message) {
        super(message);
    }

    public OutOfRangeAgeException(final Throwable cause) {
        super(cause);
    }
}
