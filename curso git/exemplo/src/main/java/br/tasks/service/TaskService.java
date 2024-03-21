package br.tasks.service;

import java.util.List;

import br.tasks.dto.TaskDTO;

/**
 * The TaskService interface provides methods to manage tasks.
 */

public interface TaskService {
  public List<TaskDTO> createTask(TaskDTO task);
  public List<TaskDTO> listAll();
  public List<TaskDTO> updateTask(TaskDTO task);
  public List<TaskDTO> deleteTask(Long id);
}
