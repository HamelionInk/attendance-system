package com.codeinside.attendancesystem.exception;

public class LessonDateMatchesException extends RuntimeException {

    public LessonDateMatchesException() {
        super();
    }

    public LessonDateMatchesException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LessonDateMatchesException(final String message) {
        super(message);
    }

    public LessonDateMatchesException(final Throwable cause) {
        super(cause);
    }
}
