package org.dwescbm;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HogwartsService {

    // Metodo para consultar estudiantes por casa
    public static List<Student> getStudentsByHouse(String houseName, String url, String masterName, String masterPasswd) {
        List<Student> studentList = new ArrayList<>();
        String SQLquery = "SELECT e.id_estudiante, e.nombre, e.apellido, e.id_casa, e.año_curso, e.fecha_nacimiento " +
                "FROM Estudiante e JOIN Casa c ON e.id_casa = c.id_casa WHERE c.nombre_casa = ?";

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            query.setString(1, houseName);
            ResultSet result = query.executeQuery();

            while (result.next()) {
                Student student = new Student(
                        result.getInt("id_estudiante"),
                        result.getString("nombre"),
                        result.getString("apellido"),
                        result.getInt("id_casa"),
                        result.getInt("año_curso"),
                        result.getDate("fecha_nacimiento").toLocalDate()
                );
                studentList.add(student);
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar estudiantes de la casa " + houseName);
            System.err.println(ex.getMessage());
        }
        return studentList;
    }

    // Metodo para listar asignaturas obligatorias
    public static List<Subject> getMandatorySubjects(String url, String masterName, String masterPasswd) {
        List<Subject> mandatorySubjects = new ArrayList<>();
        String SQLquery = "SELECT * FROM Asignatura WHERE obligatoria = TRUE";

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            ResultSet result = query.executeQuery();

            while (result.next()) {
                Subject subject = new Subject(
                        result.getInt("id_asignatura"),
                        result.getString("nombre_asignatura"),
                        result.getString("aula"),
                        result.getBoolean("obligatoria")
                );
                mandatorySubjects.add(subject);
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar asignaturas obligatorias.");
            System.err.println(ex.getMessage());
        }
        return mandatorySubjects;
    }

    // Metodo para obtener la mascota de un estudiante específico
    public static Pet getStudentPet(String studentName, String studentSurname, String url, String masterName, String masterPasswd) {
        String SQLquery = "SELECT m.id_mascota, m.nombre_mascota, m.especie, m.id_estudiante " +
                "FROM Mascota m JOIN Estudiante e ON m.id_estudiante = e.id_estudiante " +
                "WHERE e.nombre = ? AND e.apellido = ?";
        Pet pet = null;

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            query.setString(1, studentName);
            query.setString(2, studentSurname);
            ResultSet result = query.executeQuery();

            if (result.next()) {
                pet = new Pet(
                        result.getInt("id_mascota"),
                        result.getString("nombre_mascota"),
                        result.getString("especie"),
                        result.getInt("id_estudiante")
                );
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar la mascota del estudiante " + studentName + " " + studentSurname);
            System.err.println(ex.getMessage());
        }
        return pet;
    }

    public static void alterClassroomInSubject(Scanner in, String url, String masterName, String masterPasswd) {
        System.out.println("Dime el nombre de la asignatura a la que le vas a cambiar el aula: ");
        String subjectName = in.nextLine().trim();
        System.out.println("Dime el nuevo número del aula: ");
        String newClassroom = in.nextLine().trim();

        String SQLquery = "UPDATE Asignatura SET aula = ? WHERE nombre_asignatura = ?";

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            query.setString(1, newClassroom);
            query.setString(2, subjectName);

            int rowsUpdated = query.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("El aula de la asignatura " + subjectName + " ha sido actualizada a: " + newClassroom);
            } else {
                System.out.println("No se encontró la asignatura especificada o no se pudo actualizar el aula.");
            }
        } catch (SQLException ex) {
            System.err.println("Error al modificar el aula de la asignatura.");
            System.err.println(ex.getMessage());
        }
    }


    // Metodo para listar estudiantes sin mascota
    public static List<Student> getStudentsWithoutPet(String url, String masterName, String masterPasswd) {
        List<Student> studentList = new ArrayList<>();
        String SQLquery = "SELECT e.id_estudiante, e.nombre, e.apellido, e.id_casa, e.año_curso, e.fecha_nacimiento " +
                "FROM Estudiante e LEFT JOIN Mascota m ON e.id_estudiante = m.id_estudiante " +
                "WHERE m.id_estudiante IS NULL";

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            ResultSet result = query.executeQuery();

            while (result.next()) {
                Student student = new Student(
                        result.getInt("id_estudiante"),
                        result.getString("nombre"),
                        result.getString("apellido"),
                        result.getInt("id_casa"),
                        result.getInt("año_curso"),
                        result.getDate("fecha_nacimiento").toLocalDate()
                );
                studentList.add(student);
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar estudiantes sin mascota.");
            System.err.println(ex.getMessage());
        }
        return studentList;
    }

    // Metodo para calcular el promedio de calificaciones de un estudiante
    public static double calculateAverageGrade(String studentName, String studentSurname, String url, String masterName, String masterPasswd) {
        String SQLquery = "SELECT AVG(ea.calificacion) as promedio " +
                "FROM Estudiante e JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante " +
                "WHERE e.nombre = ? AND e.apellido = ?";
        double averageGrade = 0;

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            query.setString(1, studentName);
            query.setString(2, studentSurname);
            ResultSet result = query.executeQuery();

            if (result.next()) {
                averageGrade = result.getDouble("promedio");
            }
        } catch (SQLException ex) {
            System.err.println("Error al calcular promedio de calificaciones de " + studentName + " " + studentSurname);
            System.err.println(ex.getMessage());
        }
        return averageGrade;
    }

    // Metodo para listar estudiantes matriculados en una asignatura específica
    public static List<Student> getEnrolledStudentsInSubject(String subjectName, String url, String masterName, String masterPasswd) {
        List<Student> studentList = new ArrayList<>();
        String SQLquery = "SELECT e.id_estudiante, e.nombre, e.apellido, e.id_casa, e.año_curso, e.fecha_nacimiento " +
                "FROM Estudiante e JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante " +
                "JOIN Asignatura a ON a.id_asignatura = ea.id_asignatura " +
                "WHERE a.nombre_asignatura = ?";

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            query.setString(1, subjectName);
            ResultSet result = query.executeQuery();

            while (result.next()) {
                Student student = new Student(
                        result.getInt("id_estudiante"),
                        result.getString("nombre"),
                        result.getString("apellido"),
                        result.getInt("id_casa"),
                        result.getInt("año_curso"),
                        result.getDate("fecha_nacimiento").toLocalDate()
                );
                studentList.add(student);
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar estudiantes matriculados en " + subjectName);
            System.err.println(ex.getMessage());
        }
        return studentList;
    }

    // Metodo para desmatricular a un estudiante de una asignatura específica
    public static void deregisterStudentFromSubject(int studentId, int subjectId, String url, String masterName, String masterPasswd) {
        String SQLquery = "DELETE FROM Estudiante_Asignatura WHERE id_estudiante = ? AND id_asignatura = ?";

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            query.setInt(1, studentId);
            query.setInt(2, subjectId);
            int rowsAffected = query.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Estudiante desmatriculado con éxito.");
            } else {
                System.out.println("No se encontró el estudiante o la asignatura especificada.");
            }
        } catch (SQLException ex) {
            System.err.println("Error al desmatricular estudiante de la asignatura.");
            System.err.println(ex.getMessage());
        }
    }

    // Metodo para obtener todas las casas de la base de datos
    public static List<House> getHouses(String url, String masterName, String masterPasswd) {
        List<House> housesList = new ArrayList<>();
        String SQLquery = "SELECT id_casa, nombre_casa, fundador, jefe_casa, fantasma FROM Casa";

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            ResultSet result = query.executeQuery();
            while (result.next()) {
                House house = new House(
                        result.getInt("id_casa"),
                        result.getString("nombre_casa"),
                        result.getString("fundador"),
                        result.getString("jefe_casa"),
                        result.getString("fantasma")
                );
                housesList.add(house);
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar casas.");
            System.err.println(ex.getMessage());
        }
        return housesList;
    }

    // Metodo para obtener todos los estudiantes de la base de datos
    public static List<Student> getStudents(String url, String masterName, String masterPasswd) {
        List<Student> studentList = new ArrayList<>();
        String SQLquery = "SELECT id_estudiante, nombre, apellido, id_casa, año_curso, fecha_nacimiento FROM Estudiante";

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            ResultSet result = query.executeQuery();
            while (result.next()) {
                Student student = new Student(
                        result.getInt("id_estudiante"),
                        result.getString("nombre"),
                        result.getString("apellido"),
                        result.getInt("id_casa"),
                        result.getInt("año_curso"),
                        result.getDate("fecha_nacimiento").toLocalDate()
                );
                studentList.add(student);
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar estudiantes.");
            System.err.println(ex.getMessage());
        }
        return studentList;
    }

    // Metodo para insertar un nuevo estudiante en la base de datos
    public static void insertNewStudent(Scanner in, List<Student> studentList, String url, String masterName, String masterPasswd) {
        System.out.println("    - Dime los datos del estudiante a insertar: ");


        System.out.print("Nombre: ");
        String name = in.nextLine().trim();
        System.out.print("Apellido: ");
        String surname = in.nextLine().trim();
        System.out.print("ID de la casa: ");
        int houseID = in.nextInt();
        System.out.print("Curso (año): ");
        int year = in.nextInt();
        System.out.print("Fecha nacimiento (yyyy-MM-dd): ");
        String birthDateInput = in.next().trim();
        LocalDate birthDate = LocalDate.parse(birthDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        in.nextLine(); // Consumir el salto de línea

        Student newStudent = new Student(name, surname, houseID, year, birthDate);

        String SQLquery = "INSERT INTO Estudiante (nombre, apellido, id_casa, año_curso, fecha_nacimiento) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, masterName, masterPasswd);
             PreparedStatement query = connection.prepareStatement(SQLquery)) {

            query.setString(1, newStudent.getName());
            query.setString(2, newStudent.getSurname());
            query.setInt(3, newStudent.getId_house());
            query.setInt(4, newStudent.getCourseYear());
            query.setDate(5, Date.valueOf(newStudent.getDateOfBirth()));

            int rowsInserted = query.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Estudiante insertado con éxito.");
            } else {
                System.out.println("No se pudo insertar al estudiante.");
            }
        } catch (SQLException ex) {
            System.err.println("Error al insertar un nuevo estudiante.");
            System.err.println(ex.getMessage());
        }
    }

    // Metodo para contar la cantidad de estudiantes en cada casa
    public static void studentsAmountByHouse(List<Student> studentList, List<House> houseList) {
        for (House house : houseList) {
            int counter = 0;
            for (Student student : studentList) {
                if (student.getId_house() == house.getId_house()) {
                    counter++;
                }
            }
            System.out.println(house.getHouseName() + ": " + counter + " estudiantes");
        }
    }
}
