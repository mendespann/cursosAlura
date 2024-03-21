package br.tasks.service;

import java.util.List;

import br.tasks.dto.TaskDTO;

/**
 * The TaskService interface provides methods to manage tasks.
 */
public interface TaskService {

  /**
   * Get all tasks.
   * 
   * @return a list of TaskDTO objects representing all tasks.
   */
  public List<TaskDTO> getAllTasks();

  /**
   * Get task by its ID.
   * 
   * @param id the ID of the task to retrieve
   * @return a TaskDTO object representing the task with the specified ID
   */
  public TaskDTO getTaskById(int id);

  /**
   * Creates a new task.
   * 
   * @param task the TaskDTO object representing the task to create
   * @return a TaskDTO object representing the created task
   */
  public TaskDTO createTask(TaskDTO task);

  /**
   * Updates an existing task.
   * 
   * @param id   the ID of the task to update
   * @param task the TaskDTO object to update
   * @return a TaskDTO object representing the updated task
   */
  public TaskDTO updateTask(int id, TaskDTO task);

  /**
   * Deletes a task by its ID.
   * 
   * @param id the ID of the task to delete
   */
  public void deleteTask(int id);
}
