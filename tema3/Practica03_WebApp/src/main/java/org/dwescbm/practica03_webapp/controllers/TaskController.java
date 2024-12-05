package org.dwescbm.practica03_webapp.controllers;

import org.dwescbm.practica03_webapp.entities.Task;
import org.dwescbm.practica03_webapp.entities.Worker;
import org.dwescbm.practica03_webapp.services.TaskService;
import org.dwescbm.practica03_webapp.services.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final WorkerService workerService;

    public TaskController(TaskService taskService, WorkerService workerService) {
        this.taskService = taskService;
        this.workerService = workerService;
    }

    // Página para listar todas las tareas
    @GetMapping
    public String listTasks(Model model) {
        List<Task> tasks = taskService.listAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks/list"; // Referencia a la plantilla HTML
    }

    // Manejar el filtro de tareas
    @PostMapping("/filter")
    public String filterTasks(@RequestParam(required = false) String taskName,
                              @RequestParam(required = false) String estate,
                              Model model) {
        List<Task> filteredTasks = taskService.filterTasks(taskName, estate);
        model.addAttribute("tasks", filteredTasks);
        return "tasks/list"; // Reutiliza la plantilla de listar tareas
    }

    // Página para crear una tarea (formulario)
    @GetMapping("/create")
    public String createTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("workers", workerService.listAllWorkers());
        return "tasks/create";
    }

    // Procesar la creación de una tarea
    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, @RequestParam List<Long> workers) {
        List<Worker> assignedWorkers = workerService.getWorkersByIds(workers);
        task.setWorkers(assignedWorkers);
        taskService.createTask(task);
        return "redirect:/tasks";
    }


    // Página para editar una tarea (formulario)
    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id).orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada."));
        List<Worker> workers = workerService.listAllWorkers();
        model.addAttribute("task", task);
        model.addAttribute("workers", workers);
        return "tasks/edit";
    }

    // Procesar la edición de una tarea
    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task, @RequestParam List<Long> workers) {
        List<Worker> assignedWorkers = workerService.getWorkersByIds(workers);
        task.setWorkers(assignedWorkers);
        taskService.updateTask(id, task);
        return "redirect:/tasks";
    }

}