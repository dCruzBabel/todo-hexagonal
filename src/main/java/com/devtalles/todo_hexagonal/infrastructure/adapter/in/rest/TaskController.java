package com.devtalles.todo_hexagonal.infrastructure.adapter.in.rest;

import com.devtalles.todo_hexagonal.application.port.in.CompleteTaskUseCase;
import com.devtalles.todo_hexagonal.application.port.in.CreateTaskUseCase;
import com.devtalles.todo_hexagonal.application.port.in.GetTaskUseCase;
import com.devtalles.todo_hexagonal.application.port.in.ListTaskUseCase;
import com.devtalles.todo_hexagonal.application.port.in.ReopenTaskUseCase;
import com.devtalles.todo_hexagonal.domain.model.Task;
import com.devtalles.todo_hexagonal.infrastructure.adapter.in.rest.dto.CreateTaskRequest;
import com.devtalles.todo_hexagonal.infrastructure.adapter.in.rest.dto.TaskResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final CreateTaskUseCase createTaskUseCase;
  private final GetTaskUseCase getTaskUseCase;
  private final ListTaskUseCase listTaskUseCase;
  private final CompleteTaskUseCase completeTaskUseCase;
  private final ReopenTaskUseCase reopenTaskUseCase;

  @PostMapping
  public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
    Task task  = Task.builder()
        .title(createTaskRequest.getTitle())
        .description(createTaskRequest.getDescription()).build();

    Task saved = createTaskUseCase.create(task);

    return ResponseEntity.ok(TaskResponse.from(saved));
  }

  @GetMapping
  public ResponseEntity<List<TaskResponse>> listAll() {
    List<Task> tasks = listTaskUseCase.listAll();
    List<TaskResponse> response = tasks.stream().map(TaskResponse::from).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponse> getById(@PathVariable Long id) {
    Task task = getTaskUseCase.getById(id);
    return ResponseEntity.ok(TaskResponse.from(task));
  }

  @PatchMapping("/{id}/complete")
  public ResponseEntity<TaskResponse> complete(@PathVariable Long id) {
    Task task = completeTaskUseCase.complete(id);
    return ResponseEntity.ok(TaskResponse.from(task));
  }

  @PatchMapping("/{id}/reopen")
  public ResponseEntity<TaskResponse> reopen(@PathVariable Long id) {
    Task task = reopenTaskUseCase.reopen(id);
    return ResponseEntity.ok(TaskResponse.from(task));
  }
}
