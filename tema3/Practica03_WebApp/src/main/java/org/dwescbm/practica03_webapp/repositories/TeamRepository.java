package org.dwescbm.practica03_webapp.repositories;

import org.dwescbm.practica03_webapp.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
