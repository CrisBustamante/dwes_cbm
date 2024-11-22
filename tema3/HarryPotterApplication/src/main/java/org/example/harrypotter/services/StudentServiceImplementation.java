package org.example.harrypotter.services;

import org.example.harrypotter.entities.Student;
import org.example.harrypotter.repositories.StudentRepository;

import java.util.List;

public class StudentServiceImplementation implements StudentService {
    private StudentRepository studentRepository;

    public StudentServiceImplementation(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.getStudents();
    }

    public Student getStudentByName(String name) {
        return studentRepository.getStudent(name);
    }

    @Override
    public List<Student> getStudentsByFirstLetters(String letters) {return studentRepository.getStudentsByFirstLetters(letters);}

    @Override
    public List<Student> getStudentsByPatronusFirstLetters(String letters) {return studentRepository.getPatronusByFirstLetters(letters);}

    @Override
    public List<Student> getStudentsByNameAndPatronusFirstLetters(String name, String patronus) {return studentRepository.getStudentsByNameAndPatronusFirstLetters(name, patronus);}

    public List<Student> getStudentsByHouse(String house) {
        return studentRepository.getStudentsByHouse(house);
    }

    @Override
    public void addStudent(Student student) {
        studentRepository.addStudent(student);
    }
}