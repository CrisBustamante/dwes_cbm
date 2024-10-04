package org.dwescbm;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "animalShelter")
public class AnimalShelter {
    @JacksonXmlElementWrapper(localName = "animals")
    private List<Animal> animals = new ArrayList<>();

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimalById(String id) {
        animals.removeIf(animal -> animal.getId().equals(id));
    }

    public Animal findAnimalById(String id) {
        return animals.stream().filter(animal -> animal.getId().equals(id)).findFirst().orElse(null);
    }

    public Animal[] getAnimales() {
            return new Animal[0];
    }
}
