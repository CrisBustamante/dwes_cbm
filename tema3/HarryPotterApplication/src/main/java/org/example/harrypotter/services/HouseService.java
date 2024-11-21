package org.example.harrypotter.services;

import org.example.harrypotter.entities.House;

import java.util.List;

public interface HouseService {
    List<House> getHouses();
    House getHouseByName(String name);
    void addHouse(House house);
    void deleteHouse(String name);
    void updateHouse(String name, House house);
}
