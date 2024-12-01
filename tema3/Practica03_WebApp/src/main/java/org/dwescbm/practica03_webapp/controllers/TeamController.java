package org.dwescbm.practica03_webapp.controllers;

import org.dwescbm.practica03_webapp.entities.Team;
import org.dwescbm.practica03_webapp.services.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public String listTeams(Model model) {
        model.addAttribute("teams", teamService.findAll());
        return "teams/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("team", new Team());
        return "teams/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Team team) {
        teamService.save(team);
        return "redirect:/teams";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Team team = teamService.findById(id);
        if (team == null) {
            return "redirect:/teams";
        }
        model.addAttribute("team", team);
        return "teams/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        teamService.deleteById(id);
        return "redirect:/teams";
    }
}
