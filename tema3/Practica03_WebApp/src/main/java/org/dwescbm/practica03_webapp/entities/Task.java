package org.dwescbm.practica03_webapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "openingDate", nullable = false)
    private LocalDate openingDate;

    @Column(name = "plannedClosingDate", nullable = false)
    private LocalDate plannedClosingDate;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    private TaskEstate estate;

    @ManyToMany
    @JoinTable(
            name = "task_worker",
            joinColumns = @JoinColumn(name = "taskId"),
            inverseJoinColumns = @JoinColumn(name = "workerId")
    )
    private List<Worker> workers = new ArrayList<>();
}