package org.example.harrypotter.controllers;

import org.example.harrypotter.entities.Student;
import org.example.harrypotter.repositories.StudentRepository;
import org.example.harrypotter.services.StudentService;
import org.example.harrypotter.services.StudentServiceImplementation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {
    private StudentService studentService = new StudentServiceImplementation(new StudentRepository());

    @GetMapping("/student")
    public String studentDetail(@RequestParam String name, Model model) {
        Student student = studentService.getStudentByName(name);
        model.addAttribute("student", student);
        return "student";
    }

    @GetMapping("/students")
    public String studentOptional(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String patronus,
            Model model) {
        List<Student> students = new ArrayList<>();
        if (name != null && patronus != null) {
            students = studentService.getStudentsByNameAndPatronusFirstLetters(name, patronus);
        } else if (name != null) {
            students = studentService.getStudentsByFirstLetters(name);
        } else if (patronus != null) {
            students = studentService.getStudentsByPatronusFirstLetters(patronus);
        }
        model.addAttribute("students", students);

        return "students";
    }


}
