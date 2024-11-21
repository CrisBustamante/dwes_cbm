package org.example.harrypotter.repositories;

import org.example.harrypotter.entities.House;

import java.util.ArrayList;
import java.util.List;

public class HouseRepository {
    private List<House> houses = new ArrayList<>();

    public HouseRepository() {
        houses.add(new House("Gryffindor", "Lion", "Godric Gryffindor"));
        houses.add(new House("Hufflepuff", "Badger", "Helga Hufflepuff"));
        houses.add(new House("Ravenclaw", "Eagle", "Rowena Ravenclaw"));
        houses.add(new House("Slytherin", "Serpent", "Salazar Slytherin"));
    }

    public List<House> getHouses() {
        return houses;
    }

    public House getHouse(String name) {
        for (House house : houses) {
            if (house.getName().equals(name)) {
                return house;
            }
        }
        return null;
    }

    public void addhouse(House house){
        houses.add(house);
    }

    public void deleteHouse(String name){
        houses.removeIf(h -> h.getName().equalsIgnoreCase(name));
    }

    public void updateHouses(String name, House house) {
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getName().equalsIgnoreCase(name)) {
                houses.set(i, house);
                break;
            }
        }
    }
}
