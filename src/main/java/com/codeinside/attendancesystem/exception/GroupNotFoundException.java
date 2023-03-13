package com.codeinside.attendancesystem.exception;

public class GroupNotFoundException extends RuntimeException {

    public GroupNotFoundException() {
        super();
    }

    public GroupNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GroupNotFoundException(final String message) {
        super(message);
    }

    public GroupNotFoundException(final Throwable cause) {
        super(cause);
    }
}
