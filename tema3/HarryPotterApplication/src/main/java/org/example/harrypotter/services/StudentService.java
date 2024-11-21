package org.example.harrypotter.services;

import org.example.harrypotter.entities.House;
import org.example.harrypotter.entities.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();
    Student getStudentByName(String name);
    List<Student> getStudentsByHouse(String house);
    void addStudent(Student student);
    void deleteStudent(String name);
    void updateStudent(String name, Student student);
}
