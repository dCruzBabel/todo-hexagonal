package com.devtalles.todo_hexagonal.application.service;

import com.devtalles.todo_hexagonal.application.port.in.CompleteTaskUseCase;
import com.devtalles.todo_hexagonal.application.port.in.CreateTaskUseCase;
import com.devtalles.todo_hexagonal.application.port.in.GetTaskUseCase;
import com.devtalles.todo_hexagonal.application.port.in.ListTaskUseCase;
import com.devtalles.todo_hexagonal.application.port.in.ReopenTaskUseCase;
import com.devtalles.todo_hexagonal.application.port.out.TaskRepositoryPort;
import com.devtalles.todo_hexagonal.domain.exception.TaskNotFoundException;
import com.devtalles.todo_hexagonal.domain.model.Task;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService implements CreateTaskUseCase, GetTaskUseCase, ListTaskUseCase,
    CompleteTaskUseCase, ReopenTaskUseCase {

  private final TaskRepositoryPort taskRepositoryPort;

  @Override
  public Task create(Task task) {
    return taskRepositoryPort.save(task);
  }

  @Override
  public List<Task> listAll() {
    return taskRepositoryPort.findAll();
  }

  @Override
  public Task getById(Long id) {
    return taskRepositoryPort.findById(id).orElseThrow(
        () -> new TaskNotFoundException(id)
    );
  }

  @Override
  public Task complete(Long id) {
    Task task = taskRepositoryPort.findById(id).orElseThrow(
        () -> new TaskNotFoundException(id)
    );
    task.complete();
    return taskRepositoryPort.update(task);
  }

  @Override
  public Task reopen(Long id) {
    Task task = taskRepositoryPort.findById(id).orElseThrow(
        () -> new TaskNotFoundException(id)
    );
    task.reopen();
    return taskRepositoryPort.update(task);
  }
}
