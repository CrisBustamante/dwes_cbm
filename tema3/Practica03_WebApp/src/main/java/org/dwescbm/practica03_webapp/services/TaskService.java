package org.dwescbm.practica03_webapp.services;

import org.dwescbm.practica03_webapp.entities.Task;
import org.springframework.stereotype.Service;
import org.dwescbm.practica03_webapp.repositories.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findDelayedTasks() {
        return taskRepository.findDelayedTasks();
    }

    public List<Task> findOpenTasksOrderedByCreationDate() {
        return taskRepository.findOpenTasksOrderedByCreationDate();
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }
}
