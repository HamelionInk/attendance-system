package com.codeinside.attendancesystem.exception;

public class LessonNotFoundException extends RuntimeException {

    public LessonNotFoundException() {
        super();
    }

    public LessonNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LessonNotFoundException(final String message) {
        super(message);
    }

    public LessonNotFoundException(final Throwable cause) {
        super(cause);
    }
}
