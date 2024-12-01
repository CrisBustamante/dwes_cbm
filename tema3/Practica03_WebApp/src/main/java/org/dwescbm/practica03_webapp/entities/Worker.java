package org.dwescbm.practica03_webapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @ManyToMany
    @JoinTable(
            name = "workers_tasks", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "worker_id"), // Columna que hace referencia al trabajador
            inverseJoinColumns = @JoinColumn(name = "task_id") // Columna que hace referencia a la tarea
    )
    private Set<Task> tasks = new HashSet<>();

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    private Team team;

}
