package org.dwescbm;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HogwartsService {

    // Métodos adicionales de la práctica

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
}

