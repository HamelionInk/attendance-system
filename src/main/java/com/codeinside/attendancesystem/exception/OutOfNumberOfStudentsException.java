package com.codeinside.attendancesystem.exception;

public class OutOfNumberOfStudentsException extends RuntimeException {

    public OutOfNumberOfStudentsException() {
        super();
    }

    public OutOfNumberOfStudentsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OutOfNumberOfStudentsException(final String message) {
        super(message);
    }

    public OutOfNumberOfStudentsException(final Throwable cause) {
        super(cause);
    }
}
