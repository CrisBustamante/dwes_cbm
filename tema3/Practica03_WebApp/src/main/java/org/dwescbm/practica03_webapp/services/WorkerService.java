package org.dwescbm.practica03_webapp.services;

import org.dwescbm.practica03_webapp.entities.Worker;
import org.dwescbm.practica03_webapp.entities.Team;
import org.dwescbm.practica03_webapp.entities.Task;
import org.dwescbm.practica03_webapp.repositories.TeamRepository;
import org.dwescbm.practica03_webapp.repositories.TaskRepository;
import org.dwescbm.practica03_webapp.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;

    public WorkerService(WorkerRepository workerRepository, TaskRepository taskRepository, TeamRepository teamRepository) {
        this.workerRepository = workerRepository;
        this.taskRepository = taskRepository;
        this.teamRepository = teamRepository;
    }

    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    public Worker findById(Long id) {
        return workerRepository.findById(id).orElse(null);
    }

    public Worker save(Worker worker) {
        return workerRepository.save(worker);
    }

    public void delete(Worker worker) {
        workerRepository.deleteById(worker.getId());
    }
}
