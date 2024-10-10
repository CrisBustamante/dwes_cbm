package org.dwescbm;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AnimalService {

    private AnimalDataHandler dataHandler;

    public AnimalService() {
        this.dataHandler = new AnimalDataHandler();
    }

    public AnimalShelter loadAnimalData(Path path) throws IOException {
        return dataHandler.load(path);
    }

    public void saveAnimalData(AnimalShelter animalShelter, Path path) throws IOException {
        dataHandler.save(animalShelter, path);
    }

    public void addAnimal(AnimalShelter animalShelter, Scanner in) {
        Animal animal = createAnimal(in);
        animalShelter.getAnimalList().add(animal);
    }

    public void removeAnimal(AnimalShelter animalShelter, Scanner in) {
        Animal animal = findAnimal(animalShelter, in);
        if (animal != null) {
            animalShelter.getAnimalList().remove(animal);
        } else {
            System.out.println("Animal no encontrado.");
        }
    }

    public String consultAnimal(AnimalShelter animalShelter, Scanner in) {
        Animal animal = findAnimal(animalShelter, in);
        return animal != null ? animal.toString() : "Animal no encontrado.";
    }

    public void showAllAnimals(AnimalShelter animalShelter) {
        animalShelter.getAnimalList().forEach(System.out::println);
    }

    // Crear un nuevo animal
    private Animal createAnimal(Scanner in) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nINTRODUCE LOS DATOS DEL ANIMAL");
        System.out.print("· ID: ");
        int id = in.nextInt();
        in.nextLine(); // Limpiar el buffer

        System.out.print("· NOMBRE: ");
        String name = in.nextLine();

        System.out.print("· ESPECIE: ");
        String species = in.nextLine();

        System.out.print("· EDAD: ");
        int age = in.nextInt();

        Animal.AnimalSex sex = selectAnimalSex(in);

        System.out.print("· FECHA DE INGRESO (yyyy-MM-dd): ");
        String entryDateStr = in.next();
        LocalDate entryDate = LocalDate.parse(entryDateStr, formatter);

        boolean adopted = selectAdopted(in);

        return new Animal(id, name, species, age, sex, entryDate, adopted);
    }

    // Selección de sexo del animal
    private Animal.AnimalSex selectAnimalSex(Scanner in) {
        int option;
        do {
            System.out.print("\n· SEXO: \n1) Macho.\n2) Hembra.\nElige una opción: ");
            option = in.nextInt();
            if (option == 1) return Animal.AnimalSex.Macho;
            if (option == 2) return Animal.AnimalSex.Hembra;
            System.out.println("Selecciona una opción válida.");
        } while (true);
    }

    // Selección de adopción
    private boolean selectAdopted(Scanner in) {
        int option;
        do {
            System.out.print("\n· ESTÁ ADOPTADO: \n1) Sí.\n2) No.\nElige una opción: ");
            option = in.nextInt();
            if (option == 1) return true;
            if (option == 2) return false;
            System.out.println("Selecciona una opción válida.");
        } while (true);
    }

    // Buscar un animal por nombre
    private Animal findAnimal(AnimalShelter animalShelter, Scanner in) {
        System.out.print("\n¿Cuál es el nombre del animal?: ");
        String name = in.next();
        return animalShelter.getAnimalList().stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
