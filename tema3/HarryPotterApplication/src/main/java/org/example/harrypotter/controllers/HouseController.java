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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class HouseController {
    private HouseService houseService = new HouseServiceImplementation(new HouseRepository());
    private StudentService studentService = new StudentServiceImplementation(new StudentRepository());
    private Random random = new Random();

    @GetMapping("/houses")
    public String houseList(Model model) {
        List<House> houses = houseService.getHouses();
        model.addAttribute("houses", houses);
        return "houses";
    }

    @GetMapping("/house/{name}")
    public String houseDetail(@PathVariable String name, Model model) {
        House house = houseService.getHouseByName(name);
        model.addAttribute("house", house);
        List<Student> students = studentService.getStudentsByHouse(name);
        model.addAttribute("students", students);

        return "house";

    }

    @GetMapping("/houses/create")
    public String addHouse(Model model) {
        model.addAttribute("house", new House());
        return "create-house";
    }

    @PostMapping("/houses/create")
    public String addHousePost(House house) {
        houseService.addHouse(house);
        return "redirect:/houses";
    }

    @GetMapping("/house/update/{name}")
    public String updateHouse(@PathVariable String name, Model model) {
        model.addAttribute("house", houseService.getHouseByName(name));
        return "update-house";
    }

    @PostMapping("/house/update/{name}")
    public String updateHouse(@PathVariable String name, House house) {
        houseService.updateHouse(name, house);
        return "redirect:/houses";
    }

    @GetMapping("/house/delete/{name}")
    public String deleteHouse(@PathVariable String name) {
        houseService.deleteHouse(name);
        return "redirect:/houses";
    }

    @GetMapping("/house/createStudent/{name}")
    public String students(@PathVariable String name, Model model) {
        model.addAttribute("house", houseService.getHouseByName(name));
        model.addAttribute("student", new Student());
        return "create-student";
    }

    @PostMapping("/house/createStudent/{name}")
    public String createStudent(Student student, @PathVariable String name) {
        student.setHouse(houseService.getHouseByName(name));
        studentService.addStudent(student);
        return "redirect:/house/{name}";
    }

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

    @GetMapping("/")
    public String index(Model model) {
        List<House> houses = houseService.getHouses();
        House house = houses.get(random.nextInt(houses.size()));
        model.addAttribute("house", house);
        List<Student> students = studentService.getStudents();
        Student student = students.get(random.nextInt(students.size()));
        model.addAttribute("student", student);
        return "index";
    }

}