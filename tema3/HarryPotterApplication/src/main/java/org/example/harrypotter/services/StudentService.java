package org.example.harrypotter.services;

import org.example.harrypotter.entities.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();
    Student getStudentByName(String name);
    List<Student> getStudentsByFirstLetters(String letters);
    List<Student> getStudentsByPatronusFirstLetters(String letters);
    List<Student> getStudentsByNameAndPatronusFirstLetters(String name, String patronus);
    List<Student> getStudentsByHouse(String house);
    void addStudent(Student student);
}