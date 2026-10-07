package com.devtalles.todo_hexagonal.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {
  @EqualsAndHashCode.Include
  private Long id;
  private String title;
  private String description;
  private TaskStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime completedAt;

  public void complete() {
    if (status == TaskStatus.COMPLETED) {
      throw new IllegalStateException("Task is already completed");
    }
    this.status = TaskStatus.COMPLETED;
    this.completedAt = LocalDateTime.now();
  }

  public void  reopen() {
    if (status == TaskStatus.PENDING) {
      throw new IllegalStateException("Task is already pending");
    }
    this.status = TaskStatus.PENDING;
    this.completedAt = null;
  }

  public void initDefaults() {
    if (this.status == null) {
      this.status = TaskStatus.PENDING;
    }
    if (this.createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }
}
