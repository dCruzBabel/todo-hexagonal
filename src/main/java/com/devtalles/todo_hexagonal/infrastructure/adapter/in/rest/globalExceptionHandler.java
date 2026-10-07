package com.devtalles.todo_hexagonal.infrastructure.adapter.in.rest;

import com.devtalles.todo_hexagonal.domain.exception.TaskNotFoundException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class globalExceptionHandler {

  @ExceptionHandler(TaskNotFoundException.class)
  public ProblemDetail handleTaskNotFoundException(TaskNotFoundException ex) {
    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

    problemDetail.setTitle("Task Not Found");

    return problemDetail;
  }

  @ExceptionHandler(IllegalStateException.class)
  public ProblemDetail handleIllegalStateException(IllegalStateException ex) {
    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

    problemDetail.setTitle("Invalid Task State");

    return problemDetail;
  }



  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationError(MethodArgumentNotValidException ex) {

    List<String> errors = ex.getBindingResult().getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .toList();

    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");

    problemDetail.setTitle("Validation Error");
    problemDetail.setProperty("errors", errors);

    return problemDetail;
  }

}
