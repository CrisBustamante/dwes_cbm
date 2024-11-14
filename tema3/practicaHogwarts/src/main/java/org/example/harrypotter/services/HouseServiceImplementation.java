package org.example.harrypotter.services;

import org.example.harrypotter.entities.House;
import org.example.harrypotter.repositories.HouseRepository;

import java.util.List;

public class HouseServiceImplementation implements HouseService{
    private HouseRepository houseRepository;

    public HouseServiceImplementation(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> getHouses() {
        return houseRepository.getHouses();
    }

    @Override
    public List<House> getHouse() {
        return List.of();
    }

    @Override
    public House getHouseByName(String name) {
        return houseRepository.getHouse(name);
    }

}
