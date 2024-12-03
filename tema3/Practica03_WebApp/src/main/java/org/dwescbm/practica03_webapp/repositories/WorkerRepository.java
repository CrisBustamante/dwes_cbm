package org.dwescbm.practica03_webapp.repositories;

import org.dwescbm.practica03_webapp.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
}