package org.example.harrypotter.controllers;

import org.example.harrypotter.entities.House;
import org.example.harrypotter.entities.Student;
import org.example.harrypotter.services.HouseService;
import org.example.harrypotter.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HouseController {

    private HouseService houseService;

    private StudentService studentService;

    @GetMapping("/houses")
    public String listHouses(Model model) {
        List<House> houses = houseService.getHouse();
        model.addAttribute("houses", houses);
        return "houses";
    }

    @GetMapping("/house/{name}")
    public String showHouse(@PathVariable String name, Model model) {
        House house = houseService.getHouseByName(name);
        List<Student> students = studentService.getStudentsByHouse(name);
        model.addAttribute("house", house);
        model.addAttribute("students", students);
        return "house";
    }
}
