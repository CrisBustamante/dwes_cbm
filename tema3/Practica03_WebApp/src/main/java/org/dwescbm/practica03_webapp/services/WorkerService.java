package org.dwescbm.practica03_webapp.services;

import org.dwescbm.practica03_webapp.entities.Worker;
import org.dwescbm.practica03_webapp.repositories.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;
    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    // Crear un trabajador
    public Worker createWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    // Listar todos los trabajadores
    public List<Worker> listAllWorkers() {
        return workerRepository.findAll();
    }

    public List<Worker> getWorkersByIds(List<Long> ids) {
        return workerRepository.findAllById(ids);
    }

    // Ver detalles de un trabajador por ID, incluyendo sus tareas asignadas
    public Optional<Worker> getWorkerById(Long id) {
        return workerRepository.findById(id);
    }

    // Modificar los datos de un trabajador
    public Worker updateWorker(Long id, Worker updatedWorker) {
        return workerRepository.findById(id)
                .map(worker -> {
                    worker.setName(updatedWorker.getName());
                    worker.setAge(updatedWorker.getAge());
                    worker.setTeam(updatedWorker.getTeam());
                    worker.setTasks(updatedWorker.getTasks());
                    return workerRepository.save(worker);
                }).orElseThrow(() -> new IllegalArgumentException("Trabajador no encontrado con ID: " + id));
    }
}