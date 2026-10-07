package com.devtalles.todo_hexagonal.domain.exception;

public class TaskNotFoundException extends RuntimeException {
  public TaskNotFoundException(Long id) {
    super("No task found with id: " + id);
  }
}
