package org.example.harrypotter.controllers;

import org.example.harrypotter.entities.Student;
import org.example.harrypotter.repositories.StudentRepository;
import org.example.harrypotter.services.StudentService;
import org.example.harrypotter.services.StudentServiceImplementation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {
    private StudentService studentService = new StudentServiceImplementation(new StudentRepository());


    @GetMapping("/student")
    public String ahowStudentDetail(@RequestParam String name, Model model){
        model.addAttribute("student", studentService.getStudentByName(name));
        return "student";
    }


    @GetMapping("/students/create")
    public String addStudent(Model model){
        model.addAttribute("student", new Student());
        return "create-student";
    }
    @PostMapping("/student/create")
    public String addStudent(Student student){
        studentService.addStudent(student);
        return "redirect:/students";
    }


    @GetMapping("/student/delete/{name}")
    public String deleteStudent(@PathVariable String name){
        studentService.deleteStudent(name);
        return "redirect:/students";
    }


    @GetMapping("/student/update/{name}")
    public String updateStudent(@PathVariable String name, Model model){
        model.addAttribute("student", studentService.getStudentByName(name));
        return "update-student";
    }
    @PostMapping("/student/update/{name}")
    public String updateStudent(@PathVariable String name, Student student){
        studentService.updateStudent(name, student);
        return "redirect:/students";
    }
}
