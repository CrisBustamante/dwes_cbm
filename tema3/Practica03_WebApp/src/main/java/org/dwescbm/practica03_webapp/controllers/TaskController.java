package org.dwescbm.practica03_webapp.controllers;

import org.dwescbm.practica03_webapp.entities.Task;
import org.dwescbm.practica03_webapp.entities.Team;
import org.dwescbm.practica03_webapp.entities.Worker;
import org.dwescbm.practica03_webapp.services.TeamService;
import org.dwescbm.practica03_webapp.services.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.dwescbm.practica03_webapp.services.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TeamService teamService;
    private final WorkerService workerService;

    public TaskController(TaskService taskService, TeamService teamService, WorkerService workerService) {
        this.taskService = taskService;
        this.teamService = teamService;
        this.workerService = workerService;
    }

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("teams", teamService.findAll());
        model.addAttribute("workers", workerService.findAll());
        return "tasks/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Task task, BindingResult result) {
        if (result.hasErrors()) {
            return "tasks/form";
        }
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        if (task == null) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", task);
        model.addAttribute("teams", teamService.findAll());
        model.addAttribute("workers", workerService.findAll());
        return "tasks/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }

}
