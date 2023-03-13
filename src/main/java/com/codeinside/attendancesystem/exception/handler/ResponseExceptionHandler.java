package com.codeinside.attendancesystem.exception.handler;

import com.codeinside.attendancesystem.exception.LessonDateMatchesException;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.exception.LessonNotFoundException;
import com.codeinside.attendancesystem.exception.OutOfNumberOfStudentsException;
import com.codeinside.attendancesystem.exception.OutOfRangeAgeException;
import com.codeinside.attendancesystem.exception.StudentNotFoundException;
import com.codeinside.attendancesystem.exception.CoachNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final BindingResult result = ex.getBindingResult();
        final GenericResponseExceptionHandler bodyOfResponse = new GenericResponseExceptionHandler(result.getFieldErrors(), result.getGlobalErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final BindingResult result = ex.getBindingResult();
        final GenericResponseExceptionHandler bodyOfResponse = new GenericResponseExceptionHandler(result.getAllErrors(), result.getObjectName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ OutOfRangeAgeException.class })
    public ResponseEntity<?> handleOutOfRangeAgeException(final RuntimeException ex, final WebRequest request) {
        final GenericResponseExceptionHandler bodyOfResponse = new GenericResponseExceptionHandler("Out of range Age", "OutOfRangeAge");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ GroupNotFoundException.class })
    public ResponseEntity<?> handleGroupNotFoundException(final RuntimeException ex, final WebRequest request) {
        final GenericResponseExceptionHandler bodyOfResponse = new GenericResponseExceptionHandler("This group not found", "GroupNotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ StudentNotFoundException.class })
    public ResponseEntity<?> handleStudentNotFoundException(final RuntimeException ex, final WebRequest request) {
        final GenericResponseExceptionHandler bodyOfResponse = new GenericResponseExceptionHandler("This student not found", "StudentNotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ CoachNotFoundException.class })
    public ResponseEntity<?> handleTrainerNotFoundException(final RuntimeException ex, final WebRequest request) {
        final GenericResponseExceptionHandler bodyOfResponse = new GenericResponseExceptionHandler("This Trainer not found", "TrainerNotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ LessonNotFoundException.class })
    public ResponseEntity<?> handleClassesNotFoundException(final RuntimeException ex, final WebRequest request) {
        final GenericResponseExceptionHandler bodyOfResponse = new GenericResponseExceptionHandler("This Classes not found", "ClassesNotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ OutOfNumberOfStudentsException.class })
    public ResponseEntity<?> handleOutOfNumberStudentsException(final RuntimeException ex, final WebRequest request) {
        final GenericResponseExceptionHandler bodyOfResponse = new GenericResponseExceptionHandler("Limit students for this group", "OutOfNumberStudents");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ LessonDateMatchesException.class })
    public ResponseEntity<?> handleClassesDateMatchesException(final  RuntimeException ex, final WebRequest request) {
        final GenericResponseExceptionHandler bodyOfResponse = new GenericResponseExceptionHandler("Date for Class matches", "ClassesDateMatches");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


}
