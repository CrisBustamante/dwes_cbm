package org.dwescbm.practica03_webapp.controllers;

import org.dwescbm.practica03_webapp.entities.Team;
import org.dwescbm.practica03_webapp.entities.Worker;
import org.dwescbm.practica03_webapp.services.TeamService;
import org.dwescbm.practica03_webapp.services.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final WorkerService workerService;

    public TeamController(TeamService teamService, WorkerService workerService) {
        this.teamService = teamService;
        this.workerService = workerService;
    }

    // Página para listar todos los equipos
    @GetMapping
    public String listTeams(Model model) {
        List<Team> teams = teamService.listAllTeams();
        model.addAttribute("teams", teams);
        return "teams/list"; // Referencia a la plantilla HTML
    }

    // Página para crear un equipo (formulario)
    @GetMapping("/create")
    public String createTeamForm(Model model) {
        model.addAttribute("team", new Team());
        return "teams/create"; // Referencia a la plantilla HTML
    }

    // Procesar la creación de un equipo
    @PostMapping("/create")
    public String createTeam(@ModelAttribute Team team) {
        teamService.createTeam(team);
        return "redirect:/teams"; // Redirige a la lista de equipos
    }

    // Página para editar un equipo (formulario)
    @GetMapping("/edit/{id}")
    public String editTeamForm(@PathVariable Long id, Model model) {
        Team team = teamService.getTeamById(id).orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado."));
        model.addAttribute("team", team);
        return "teams/edit"; // Referencia a la plantilla HTML
    }

    // Procesar la edición de un equipo
    @PostMapping("/edit/{id}")
    public String updateTeam(@PathVariable Long id, @ModelAttribute Team team) {
        teamService.updateTeam(id, team);
        return "redirect:/teams";
    }

    // Página para ver los detalles de un equipo
    @GetMapping("/{id}")
    public String viewTeam(@PathVariable Long id, Model model) {
        Team team = teamService.getTeamById(id).orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado."));
        List<Worker> workers = team.getWorkers();
        model.addAttribute("team", team);
        model.addAttribute("workers", workers);
        return "teams/view"; // Referencia a la plantilla HTML
    }
}
