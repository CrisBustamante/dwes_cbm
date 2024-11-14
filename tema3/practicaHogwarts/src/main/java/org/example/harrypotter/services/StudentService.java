package org.example.harrypotter.services;

import org.example.harrypotter.entities.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();
    Student getStudentByName(String name);
    List<Student> getStudentsByHouse(String house);
}
