package org.example.harrypotter.controllers;

import org.example.harrypotter.entities.Student;
import org.example.harrypotter.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    private StudentService studentService;

    @GetMapping("/student")
    public String showStudent(@RequestParam String name, Model model) {
        Student student = studentService.getStudentByName(name);
        model.addAttribute("student", student);
        return "student";
    }
}
