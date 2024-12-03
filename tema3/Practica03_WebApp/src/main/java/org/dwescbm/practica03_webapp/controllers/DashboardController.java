package org.dwescbm.practica03_webapp.controllers;

import org.dwescbm.practica03_webapp.entities.Task;
import org.dwescbm.practica03_webapp.services.TaskService;
import org.dwescbm.practica03_webapp.services.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final TaskService taskService;
    private final WorkerService workerService;

    public DashboardController(TaskService taskService, WorkerService workerService) {
        this.taskService = taskService;
        this.workerService = workerService;
    }

    // Mostrar el cuadro de mando
    @GetMapping
    public String showDashboard(@RequestParam(required = false) Long workerId, Model model) {
        // Tareas retrasadas
        List<Task> delayedTasks = taskService.getDelayedTasks();

        // Tareas en curso de un trabajador
        List<Task> tasksInProgress = workerId != null ? taskService.getTasksInProgressByWorker(workerId) : null;

        // Tareas abiertas ordenadas por fecha
        List<Task> openTasksOrdered = taskService.getOpenTasksOrderedByOpeningDate();

        // Estadísticas de tipos de tareas
        Map<String, Double> taskTypeStats = taskService.getTaskTypeStatistics();

        model.addAttribute("delayedTasks", delayedTasks);
        model.addAttribute("tasksInProgress", tasksInProgress);
        model.addAttribute("openTasksOrdered", openTasksOrdered);
        model.addAttribute("taskTypeStats", taskTypeStats);
        model.addAttribute("workers", workerService.listAllWorkers()); // Para seleccionar un trabajador
        model.addAttribute("selectedWorkerId", workerId);
        return "dashboard";
    }
}