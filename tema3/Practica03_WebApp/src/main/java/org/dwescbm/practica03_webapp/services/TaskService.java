package org.dwescbm.practica03_webapp.services;

import org.dwescbm.practica03_webapp.entities.Task;
import org.dwescbm.practica03_webapp.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Crear una tarea
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Listar todas las tareas
    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    // Obtener una tarea por ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Modificar los datos de una tarea
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(updatedTask.getName());
                    task.setDescription(updatedTask.getDescription());
                    task.setOpeningDate(updatedTask.getOpeningDate());
                    task.setPlannedClosingDate(updatedTask.getPlannedClosingDate());
                    task.setType(updatedTask.getType());
                    task.setEstate(updatedTask.getEstate());
                    task.setWorkers(updatedTask.getWorkers());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada con ID: " + id));
    }

    // Filtrar tareas por nombre y estado
    public List<Task> filterTasks(String taskName, String estate) {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .filter(task -> (taskName == null || task.getName().contains(taskName)) &&
                        (estate == null || task.getEstate().toString().equalsIgnoreCase(estate)))
                .collect(Collectors.toList());
    }

    // Obtener tareas retrasadas
    public List<Task> getDelayedTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getPlannedClosingDate().isBefore(LocalDate.now()) &&
                        !task.getEstate().toString().equalsIgnoreCase("CERRADA"))
                .collect(Collectors.toList());
    }

    // Obtener tareas en curso de un trabajador
    public List<Task> getTasksInProgressByWorker(Long workerId) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getWorkers().stream()
                        .anyMatch(worker -> worker.getId().equals(workerId)) &&
                        task.getEstate().toString().equalsIgnoreCase("EN_PROGRESO"))
                .collect(Collectors.toList());
    }

    // Obtener tareas abiertas ordenadas por fecha
    public List<Task> getOpenTasksOrderedByOpeningDate() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getEstate().toString().equalsIgnoreCase("ABIERTA"))
                .sorted(Comparator.comparing(Task::getOpeningDate))
                .collect(Collectors.toList());
    }

    // Obtener estadísticas de tipos de tareas
    public Map<String, Double> getTaskTypeStatistics() {
        List<Task> tasks = taskRepository.findAll();
        long totalTasks = tasks.size();
        return tasks.stream()
                .collect(Collectors.groupingBy(task -> task.getType().toString(), Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (entry.getValue() * 100.0) / totalTasks
                ));
    }
}