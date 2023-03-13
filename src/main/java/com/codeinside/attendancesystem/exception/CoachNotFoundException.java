package com.codeinside.attendancesystem.exception;

public class CoachNotFoundException extends RuntimeException {

    public CoachNotFoundException() {
        super();
    }

    public CoachNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CoachNotFoundException(final String message) {
        super(message);
    }

    public CoachNotFoundException(final Throwable cause) {
        super(cause);
    }
}
