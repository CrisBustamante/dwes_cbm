package org.dwescbm.practica03_webapp.repositories;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.dwescbm.practica03_webapp.entities.Team;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT t FROM Team t WHERE t.name = ?1")
    List<Team> findByName(String name);
}
