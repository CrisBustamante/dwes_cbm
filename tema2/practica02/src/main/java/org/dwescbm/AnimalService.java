package org.dwescbm;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AnimalService {

    public AnimalShelter readXmlToList(Path path) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(path.toFile(), new TypeReference<AnimalShelter>() {});
    }

    public void writeListToXml(AnimalShelter animalList, Path path) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.writeValue(path.toFile(), animalList);
    }

    public void addAnimal(AnimalShelter animalList, Scanner in) {
        Animal animal = createAnimal(in);
        animalList.getAnimalList().add(animal);
    }

    public void removeAnimal(AnimalShelter animalList, Scanner in) {
        Animal animal = findAnimal(animalList, in);
        if (animal != null) {
            animalList.getAnimalList().remove(animal);
        } else {
            System.out.println("Animal no encontrado.");
        }
    }

    public String consultAnimal(AnimalShelter animalList, Scanner in) {
        Animal animal = findAnimal(animalList, in);
        return animal != null ? animal.toString() : "Animal no encontrado.";
    }

    public void showAllAnimals(AnimalShelter animalList) {
        animalList.getAnimalList().forEach(System.out::println);
    }

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

    private boolean selectAdopted(Scanner in) {
        int option;
        do {
            System.out.print("\n· ESTA ADOPTADO: \n1) Sí.\n2) No.\nElige una opción: ");
            option = in.nextInt();
            if (option == 1) return true;
            if (option == 2) return false;
            System.out.println("Selecciona una opción válida.");
        } while (true);
    }

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

    private Animal findAnimal(AnimalShelter animalList, Scanner in) {
        System.out.println("\n¿Cuál es el nombre del animal?: ");
        String name = in.next();
        return animalList.getAnimalList().stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
