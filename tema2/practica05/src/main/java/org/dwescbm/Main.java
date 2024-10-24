package org.dwescbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:postgresql://practica05.chiqqxoyuuaa.us-east-1.rds.amazonaws.com:5432/f12006";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "practica05bbdd";

    public static void main(String[] args) {

        // Metodo para conectarse a la base de datos
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("CONECTADO A LA BASE DE DATOS EXITOSAMENTE.");
        } catch (SQLException ex) {
            System.out.println("ERROR: NO SE HA PODIDO CONECTAR A LA BASE DE DATOS");
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            // Imprimir el menú utilizando el metodo printMenu
            printMenu();

            // Validar entrada numérica para seleccionar opción
            while (!scanner.hasNextInt()) {
                System.out.print("Entrada inválida. Por favor, ingrese un número entero: ");
                scanner.next();
            }
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            // Ejecutar la opción correspondiente
            switch (option) {
                case 1:
                    handleStudentsByHouse(scanner);
                    break;
                case 2:
                    handleMandatorySubjects();
                    break;
                case 3:
                    handleStudentPet(scanner);
                    break;
                case 4:
                    handleStudentsWithoutPet();
                    break;
                case 5:
                    handleAverageGrades(scanner);
                    break;
                case 6:
                    handleStudentsAmountByHouse();
                    break;
                case 7:
                    handleEnrolledStudentsInSubject(scanner);
                    break;
                case 8:
                    handleInsertNewStudent(scanner);
                    break;
                case 9:
                    handleAlterClassroom(scanner);
                    break;
                case 10:
                    handleDeregisterStudentFromSubject(scanner);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (option != 0);

        scanner.close();
    }

    // Metodo para imprimir el menú
    private static void printMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Consultar estudiantes por casa");
        System.out.println("2. Listado de asignaturas obligatorias");
        System.out.println("3. Obtener mascota de un estudiante específico");
        System.out.println("4. Listar estudiantes sin mascota");
        System.out.println("5. Promedio de calificaciones de un estudiante");
        System.out.println("6. Número de estudiantes por casa");
        System.out.println("7. Estudiantes matriculados en una asignatura específica");
        System.out.println("8. Insertar nuevo estudiante");
        System.out.println("9. Modificar aula de una asignatura");
        System.out.println("10. Desmatricular a un estudiante de una asignatura");
        System.out.println("0. Salir");
        System.out.print("\nSeleccione una opción: ");
    }

    // Metodo para manejar la opción 1: Consultar estudiantes por casa
    private static void handleStudentsByHouse(Scanner scanner) {
        System.out.print("Ingrese el nombre de la casa: ");
        String houseName = scanner.nextLine().trim();
        if (houseName.isEmpty()) {
            System.out.println("Error: El nombre de la casa no puede estar vacío.");
            return;
        }
        List<Student> studentsByHouse = HogwartsService.getStudentsByHouse(houseName, DB_URL, DB_USER, DB_PASSWORD);
        if (studentsByHouse.isEmpty()) {
            System.out.println("No se encontraron estudiantes para la casa: " + houseName);
        } else {
            System.out.println("\nEstudiantes de la casa " + houseName + ":");
            studentsByHouse.forEach(student -> System.out.println(student.getName() + " " + student.getSurname()));
        }
    }

    // Metodo para manejar la opción 2: Listado de asignaturas obligatorias
    private static void handleMandatorySubjects() {
        List<Subject> mandatorySubjects = HogwartsService.getMandatorySubjects(DB_URL, DB_USER, DB_PASSWORD);
        if (mandatorySubjects.isEmpty()) {
            System.out.println("No se encontraron asignaturas obligatorias.");
        } else {
            System.out.println("\nAsignaturas obligatorias:");
            mandatorySubjects.forEach(subject -> System.out.println(subject.getSubject_name()));
        }
    }

    // Metodo para manejar la opción 3: Obtener mascota de un estudiante específico
    private static void handleStudentPet(Scanner scanner) {
        System.out.print("Ingrese el nombre del estudiante: ");
        String studentName = scanner.nextLine().trim();
        System.out.print("Ingrese el apellido del estudiante: ");
        String studentSurname = scanner.nextLine().trim();
        if (studentName.isEmpty() || studentSurname.isEmpty()) {
            System.out.println("Error: El nombre o apellido no puede estar vacío.");
            return;
        }
        Pet studentPet = HogwartsService.getStudentPet(studentName, studentSurname, DB_URL, DB_USER, DB_PASSWORD);
        if (studentPet != null) {
            System.out.println("\nMascota de " + studentName + " " + studentSurname + ": " + studentPet.getPet_name() + " (" + studentPet.getSpecies() + ")");
        } else {
            System.out.println("\nEl estudiante no tiene mascota.");
        }
    }

    // Metodo para manejar la opción 4: Listar estudiantes sin mascota
    private static void handleStudentsWithoutPet() {
        List<Student> studentsWithoutPet = HogwartsService.getStudentsWithoutPet(DB_URL, DB_USER, DB_PASSWORD);
        if (studentsWithoutPet.isEmpty()) {
            System.out.println("No se encontraron estudiantes sin mascota.");
        } else {
            System.out.println("\nEstudiantes sin mascota:");
            studentsWithoutPet.forEach(student -> System.out.println(student.getName() + " " + student.getSurname()));
        }
    }

    // Metodo para manejar la opción 5: Promedio de calificaciones de un estudiante
    private static void handleAverageGrades(Scanner scanner) {
        System.out.print("Ingrese el nombre del estudiante: ");
        String studentForGradesName = scanner.nextLine().trim();
        System.out.print("Ingrese el apellido del estudiante: ");
        String studentForGradesSurname = scanner.nextLine().trim();
        if (studentForGradesName.isEmpty() || studentForGradesSurname.isEmpty()) {
            System.out.println("Error: El nombre o apellido no puede estar vacío.");
            return;
        }
        double averageGrade = HogwartsService.calculateAverageGrade(studentForGradesName, studentForGradesSurname, DB_URL, DB_USER, DB_PASSWORD);
        System.out.println("\nPromedio de calificaciones de " + studentForGradesName + " " + studentForGradesSurname + ": " + averageGrade);
    }

    // Metodo para manejar la opción 6: Número de estudiantes por casa
    private static void handleStudentsAmountByHouse() {
        List<House> houses = HogwartsService.getHouses(DB_URL, DB_USER, DB_PASSWORD);
        List<Student> allStudents = HogwartsService.getStudents(DB_URL, DB_USER, DB_PASSWORD);
        if (houses.isEmpty() || allStudents.isEmpty()) {
            System.out.println("No se encontraron casas o estudiantes.");
        } else {
            System.out.println("\nNúmero de estudiantes por casa:");
            HogwartsService.studentsAmountByHouse(allStudents, houses);
        }
    }

    // Metodo para manejar la opción 7: Estudiantes matriculados en una asignatura específica
    private static void handleEnrolledStudentsInSubject(Scanner scanner) {
        System.out.print("Ingrese el nombre de la asignatura: ");
        String subjectName = scanner.nextLine().trim();
        if (subjectName.isEmpty()) {
            System.out.println("Error: El nombre de la asignatura no puede estar vacío.");
            return;
        }
        List<Student> enrolledStudents = HogwartsService.getEnrolledStudentsInSubject(subjectName, DB_URL, DB_USER, DB_PASSWORD);
        if (enrolledStudents.isEmpty()) {
            System.out.println("No se encontraron estudiantes matriculados en la asignatura: " + subjectName);
        } else {
            System.out.println("\nEstudiantes matriculados en " + subjectName + ":");
            enrolledStudents.forEach(student -> System.out.println(student.getName() + " " + student.getSurname()));
        }
    }

    // Metodo para manejar la opción 8: Insertar un nuevo estudiante
    private static void handleInsertNewStudent(Scanner scanner) {
        HogwartsService.insertNewStudent(scanner, HogwartsService.getStudents(DB_URL, DB_USER, DB_PASSWORD), DB_URL, DB_USER, DB_PASSWORD);
    }

    // Metodo para manejar la opción 9: Modificar aula de una asignatura
    private static void handleAlterClassroom(Scanner scanner) {
        HogwartsService.alterClassroomInSubject(scanner, DB_URL, DB_USER, DB_PASSWORD);
    }

    // Metodo para manejar la opción 10: Desmatricular a un estudiante de una asignatura
    private static void handleDeregisterStudentFromSubject(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del estudiante: ");
            int studentId = scanner.nextInt();
            System.out.print("Ingrese el ID de la asignatura: ");
            int subjectId = scanner.nextInt();
            HogwartsService.deregisterStudentFromSubject(studentId, subjectId, DB_URL, DB_USER, DB_PASSWORD);
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número válido para los ID.");
            scanner.next(); // Limpiar entrada incorrecta
        }
    }
}
