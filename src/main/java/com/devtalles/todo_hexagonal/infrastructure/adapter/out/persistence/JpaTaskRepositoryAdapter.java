package com.devtalles.todo_hexagonal.infrastructure.adapter.out.persistence;

import com.devtalles.todo_hexagonal.application.port.out.TaskRepositoryPort;
import com.devtalles.todo_hexagonal.domain.model.Task;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaTaskRepositoryAdapter implements TaskRepositoryPort {

  private final SpringDataTaskRepository springDataTaskRepository;
  private final TaskPersistenceMapper mapper;

  @Override
  public Task save(Task task) {
    task.initDefaults();
    TaskJpaEntity entity = mapper.toJpaEntity(task);
    TaskJpaEntity saved = springDataTaskRepository.save(entity);

    return mapper.toDomain(saved);
  }

  @Override
  public List<Task> findAll() {
    return springDataTaskRepository.findAll()
        .stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public Optional<Task> findById(Long id) {
    return springDataTaskRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Task update(Task task) {
    TaskJpaEntity entity = mapper.toJpaEntity(task);
    TaskJpaEntity saved = springDataTaskRepository.save(entity);
    return mapper.toDomain(saved);
  }
}