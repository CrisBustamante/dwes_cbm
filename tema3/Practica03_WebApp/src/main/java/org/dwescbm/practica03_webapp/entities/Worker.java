package org.dwescbm.practica03_webapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @ManyToMany(mappedBy = "workers")
    private List<Task> tasks = new ArrayList<>();

}