package org.dwescbm.practica03_webapp.controllers;

import org.dwescbm.practica03_webapp.entities.TeamWork;
import org.dwescbm.practica03_webapp.entities.Worker;
import org.dwescbm.practica03_webapp.entities.Task;
import org.dwescbm.practica03_webapp.services.TeamWorkService;
import org.dwescbm.practica03_webapp.services.WorkerService;
import org.dwescbm.practica03_webapp.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/workers")
public class WorkerController {

    private final WorkerService workerService;
    private final TeamWorkService teamWorkService;

    public WorkerController(WorkerService workerService, TeamWorkService teamWorkService) {
        this.workerService = workerService;
        this.teamWorkService = teamWorkService;
    }

    @GetMapping
    public String listWorkers(Model model) {
        List<Worker> workers = workerService.listAllWorkers();
        model.addAttribute("workers", workers);
        return "workers/list";
    }

    @GetMapping("/create")
    public String createWorkerForm(Model model) {
        model.addAttribute("worker", new Worker());
        model.addAttribute("teams", teamWorkService.listAllTeams());
        return "workers/create";
    }

    @PostMapping("/create")
    public String createWorker(@ModelAttribute Worker worker, @RequestParam Long teamWorkId) {
        TeamWork teamWork = teamWorkService.getTeamById(teamWorkId)
                .orElseThrow(() -> new IllegalArgumentException("Grupo de trabajo no encontrado."));
        worker.setTeamWork(teamWork);
        workerService.createWorker(worker);
        return "redirect:/workers";
    }


    @GetMapping("/edit/{id}")
    public String editWorkerForm(@PathVariable Long id, Model model) {
        Worker worker = workerService.getWorkerById(id).orElseThrow(() -> new IllegalArgumentException("Trabajador no encontrado."));
        List<TeamWork> teams = teamWorkService.listAllTeams();
        model.addAttribute("worker", worker);
        model.addAttribute("teams", teams);
        return "workers/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateWorker(@PathVariable Long id, @ModelAttribute Worker worker, @RequestParam Long teamWorkId) {
        TeamWork teamWork = teamWorkService.getTeamById(teamWorkId)
                .orElseThrow(() -> new IllegalArgumentException("Grupo de trabajo no encontrado."));
        worker.setTeamWork(teamWork);
        workerService.updateWorker(id, worker);
        return "redirect:/workers";
    }


    // Página para ver los detalles de un trabajador
    @GetMapping("/{id}")
    public String viewWorker(@PathVariable Long id, Model model) {
        Worker worker = workerService.getWorkerById(id).orElseThrow(() -> new IllegalArgumentException("Trabajador no encontrado."));
        List<Task> tasks = worker.getTasks();
        model.addAttribute("worker", worker);
        model.addAttribute("tasks", tasks);
        return "workers/view"; // Referencia a la plantilla HTML
    }
}