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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HouseController {
    private HouseService houseService = new HouseServiceImplementation(new HouseRepository());
    private StudentService studentService = new StudentServiceImplementation(new StudentRepository());

    @GetMapping("/houses")
    public String listHouses(Model model){
        List<House> houses = houseService.getHouses();
        model.addAttribute("houses", houses);
        return "houses";
    }

    @GetMapping("/house/{name}")
    public String showHouse(@PathVariable String name, Model model){
        model.addAttribute("house", houseService.getHouseByName(name));
        List<Student> students = studentService.getStudentsByHouse(name);
        model.addAttribute("students", students);
        return "house";
    }

    //CREAR CASA
    @GetMapping("/houses/create")
    public String addHouse(Model model){
        model.addAttribute("house", new House());
        return "create-house";
    }
    @PostMapping("/house/create")
    public String addHouse(House house){
        houseService.addHouse(house);
        return "redirect:/houses";
    }

    //BORRAR CASA
    @GetMapping("/house/delete/{name}")
    public String deleteHouse(@PathVariable String name){
        houseService.deleteHouse(name);
        return "redirect:/houses";
    }

    //ACTUALIZAR CASA
    @GetMapping("/house/update/{name}")
    public String updateHouse(@PathVariable String name, Model model){
        model.addAttribute("house", houseService.getHouseByName(name));
        return "update-house";
    }
    @PostMapping("/house/update/{name}")
    public String updateBook(@PathVariable String name, House house){
        houseService.updateHouse(name, house);
        return "redirect:/houses";
    }
}
