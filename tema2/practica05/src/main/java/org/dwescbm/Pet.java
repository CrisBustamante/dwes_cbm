package org.dwescbm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Pet {
    private int id_pet;
    private String pet_name;
    private String species;
    private int id_student;
}
