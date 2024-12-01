package org.dwescbm.practica03_webapp.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.dwescbm.practica03_webapp.entities.Worker;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Query("SELECT w FROM Worker w WHERE w.team.id = ?1")
    List<Worker> findByTeamId(Long teamId);

    @Query("SELECT w FROM Worker w")
    List<Worker> findAll();
}