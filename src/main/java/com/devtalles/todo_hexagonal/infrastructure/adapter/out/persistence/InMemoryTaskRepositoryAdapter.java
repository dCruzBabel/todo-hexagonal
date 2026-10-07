package com.devtalles.todo_hexagonal.infrastructure.adapter.out.persistence;

import com.devtalles.todo_hexagonal.application.port.out.TaskRepositoryPort;
import com.devtalles.todo_hexagonal.domain.model.Task;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class InMemoryTaskRepositoryAdapter implements TaskRepositoryPort {

  private final Map<Long, Task> store = new ConcurrentHashMap<>();
  private final AtomicLong idSequence = new AtomicLong(1);


  @Override
  public Task save(Task task) {
    task.initDefaults();
    if (task.getId() == null) {
      task.setId(idSequence.getAndIncrement());
    }
    store.put(task.getId(), task);
    return task;
  }

  @Override
  public Optional<Task> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public List<Task> findAll() {
    return List.copyOf(store.values());
  }

  @Override
  public Task update(Task task) {
    store.put(task.getId(), task);
    return task;
  }
}
