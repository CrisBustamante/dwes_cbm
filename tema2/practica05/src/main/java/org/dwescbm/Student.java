package org.dwescbm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Student {

    private int id_student;
    private String name;
    private String surname;
    private int id_house;
    private int courseYear;
    private LocalDate dateOfBirth;

    public Student(String name, String surname, int houseID, int year, LocalDate birthDate) {
    }
}
