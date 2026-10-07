package com.devtalles.todo_hexagonal.infrastructure.adapter.in.rest.dto;

import com.devtalles.todo_hexagonal.domain.model.TaskStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TaskResponse {

  private Long id;
  private String title;
  private String description;
  private TaskStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime completedAt;

  public static TaskResponse from(com.devtalles.todo_hexagonal.domain.model.Task task) {
    return TaskResponse.builder()
        .id(task.getId())
        .title(task.getTitle())
        .description(task.getDescription())
        .status(task.getStatus())
        .createdAt(task.getCreatedAt())
        .completedAt(task.getCompletedAt())
        .build();
  }


}
