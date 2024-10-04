package org.dwescbm;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.time.LocalDate;

public class Animal {
    @JacksonXmlProperty(isAttribute = true)
    private String id;
    private String name;
    private String species;
    private int age;
    private String sex;
    private LocalDate entryDate;
    private boolean adopted;

    public Animal(String id, String name, String species, int age, String sex, LocalDate entryDate, boolean adopted) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
        this.sex = sex;
        this.entryDate = entryDate;
        this.adopted = adopted;
    }

    @Override
    public String toString() {
        return String.format(
                "Animal {\n" +
                        "  ID: '%s',\n" +
                        "  Nombre: '%s',\n" +
                        "  Especie: '%s',\n" +
                        "  Edad: %d,\n" +
                        "  Sexo: '%s',\n" +
                        "  Fecha de Ingreso: %s,\n" +
                        "  Adoptado: %b\n" +
                        "}",
                id, name, species, age, sex, entryDate, adopted
        );
    }


    public Object getId() {
        return id;
    }
}
