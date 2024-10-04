package org.dwescbm;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        XMLOperations operations = new XMLOperations();
        AnimalShelter shelter;

        try {
            shelter = operations.loadXML();
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo XML.");
            shelter = new AnimalShelter();
        }

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("Menú:");
            System.out.println("1. Mostrar todos los animales");
            System.out.println("2. Añadir animal");
            System.out.println("3. Borrar animal");
            System.out.println("4. Consultar animal");
            System.out.println("5. Guardar y salir");
            System.out.print("Elige una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    for (Animal animal : shelter.getAnimales()) {
                        System.out.println(animal);
                    }
                    break;
                case 2:
                    System.out.print("Introduce el ID del animal: ");
                    String id = scanner.nextLine();
                    System.out.print("Introduce el nombre: ");
                    String name = scanner.nextLine();
                    System.out.print("Introduce la especie: ");
                    String species = scanner.nextLine();
                    System.out.print("Introduce la edad: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume el newline

                    // Validar el sexo
                    String sex = "";
                    while (!sex.equalsIgnoreCase("m") && !sex.equalsIgnoreCase("f")) {
                        System.out.print("Introduce el sexo (m/f): ");
                        sex = scanner.nextLine();
                        if (!sex.equalsIgnoreCase("m") && !sex.equalsIgnoreCase("f")) {
                            System.out.println("Sexo inválido. Por favor, introduce 'm' para masculino o 'f' para femenino.");
                        }
                    }

                    // Validar la fecha de ingreso
                    LocalDate entryDate = null;
                    while (entryDate == null) {
                        System.out.print("Introduce la fecha de ingreso (yyyy-mm-dd): ");
                        String entryDateStr = scanner.nextLine();
                        try {
                            entryDate = LocalDate.parse(entryDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        } catch (DateTimeParseException e) {
                            System.out.println("Fecha inválida. Por favor, introduce una fecha en formato yyyy-MM-dd.");
                        }
                    }

                    // Validar si el animal está adoptado
                    String adoptedStr = "";
                    boolean adopted = false;
                    while (!adoptedStr.equalsIgnoreCase("si") && !adoptedStr.equalsIgnoreCase("no")) {
                        System.out.print("¿Está adoptado? (si/no): ");
                        adoptedStr = scanner.nextLine();
                        if (adoptedStr.equalsIgnoreCase("si")) {
                            adopted = true;
                        } else if (adoptedStr.equalsIgnoreCase("no")) {
                            adopted = false;
                        } else {
                            System.out.println("Entrada inválida. Por favor, introduce 'si' o 'no'.");
                        }
                    }

                    Animal nuevoAnimal = new Animal(id, name, species, age, sex, entryDate, adopted);
                    shelter.addAnimal(nuevoAnimal);
                    break;
                case 3:
                    System.out.print("Introduce el ID del animal a borrar: ");
                    String idBorrar = scanner.nextLine();
                    shelter.removeAnimalById(idBorrar);
                    break;
                case 4:
                    System.out.print("Introduce el ID del animal a consultar: ");
                    String idConsultar = scanner.nextLine();
                    Animal animal = shelter.findAnimalById(idConsultar);
                    if (animal != null) {
                        System.out.println(animal);
                    } else {
                        System.out.println("Animal no encontrado.");
                    }
                    break;
                case 5:
                    try {
                        operations.saveXML(shelter);
                        System.out.println("Datos guardados correctamente.");
                    } catch (IOException e) {
                        System.out.println("Error al guardar el archivo XML.");
                    }
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (option != 5);

        scanner.close();
    }
}

