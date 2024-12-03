package org.dwescbm.practica03_webapp.services;

import org.dwescbm.practica03_webapp.entities.Team;
import org.dwescbm.practica03_webapp.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // Crear un equipo
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    // Listar todos los equipos
    public List<Team> listAllTeams() {
        return teamRepository.findAll();
    }

    // Ver detalles de un equipo por ID, incluyendo sus miembros
    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    // Modificar los datos de un equipo
    public Team updateTeam(Long id, Team updatedTeamWork) {
        return teamRepository.findById(id)
                .map(team -> {
                    team.setName(updatedTeamWork.getName());
                    team.setWorkers(updatedTeamWork.getWorkers());
                    return teamRepository.save(team);
                }).orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado con ID: " + id));
    }
}