package org.dwescbm.practica03_webapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//Anotaciones lombok
@Getter
@Setter
@ToString
@NoArgsConstructor

//Anotaciones Hibernate
@Entity
@Table(name = "tasks")
public class Task {

    //Indicamos clave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Clave primaria se genera automaticamente
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "type_improvement", nullable = false)
    private String typeImprovement;

    @Column(name = "estate", nullable = false)
    private String estate;



    @ManyToMany(mappedBy = "tasks")
    private Set<Worker> workers = new HashSet<>();

}
