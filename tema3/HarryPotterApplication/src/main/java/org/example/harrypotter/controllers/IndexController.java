package org.example.harrypotter.controllers;

import org.example.harrypotter.entities.House;
import org.example.harrypotter.entities.Student;
import org.example.harrypotter.repositories.HouseRepository;
import org.example.harrypotter.repositories.StudentRepository;
import org.example.harrypotter.services.HouseService;
import org.example.harrypotter.services.HouseServiceImplementation;
import org.example.harrypotter.services.StudentService;
import org.example.harrypotter.services.StudentServiceImplementation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Random;

@Controller
public class IndexController {

    private HouseService houseService = new HouseServiceImplementation(new HouseRepository());
    private StudentService studentService = new StudentServiceImplementation(new StudentRepository());
    private Random random = new Random();

    @GetMapping("/")
    public String index(Model model) {
        List<House> houses = houseService.getHouses();
        House house = houses.get(random.nextInt(houses.size()));
        model.addAttribute("houses", houses);
        List<Student> students = studentService.getStudents();
        Student student = students.get(random.nextInt(students.size()));
        model.addAttribute("student", student);
        return "index";
    }

}
