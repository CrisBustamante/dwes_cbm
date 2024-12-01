package org.dwescbm.practica03_webapp.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.dwescbm.practica03_webapp.entities.Task;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.worker.id = ?1")
    List<Task> findByWorkerId(Long userId);

    @Query("SELECT t FROM Task t WHERE t.worker.team.id = ?1")
    List<Task> findByTeamId(Long teamId);

    @Query("SELECT t FROM Task t WHERE t.worker.team.id = ?1 AND t.worker.id = ?2")
    List<Task> findByTeamIdAndWorkerId(Long teamId, Long userId);

    @Query("SELECT t FROM Task t WHERE t.worker.id = ?1 AND t.worker.team.id = ?2")
    List<Task> findByWorkerIdAndTeamId(Long userId, Long teamId);

}
