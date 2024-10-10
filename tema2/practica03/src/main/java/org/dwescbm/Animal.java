package org.dwescbm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    public enum AnimalSex {
        Macho, Hembra
    }

    @JsonProperty("id")
    private int id;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("especie")
    private String species;

    @JsonProperty("edad")
    private int age;

    @JsonProperty("sexo")
    private AnimalSex sex;

    @JsonProperty("fechaIngreso")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate entryDate;

    @JsonProperty("adoptado")
    private boolean adopted;

    @Override
    public String toString() {
        return "ID: " + id +
                " \nNOMBRE: " + name +
                " \nESPECIE: " + species +
                " \nEDAD: " + age +
                " \nSEXO: " + sex +
                " \nFECHA DE INGRESO: " + entryDate +
                " \nESTÁ ADOPTADO: " + (adopted ? "Sí" : "No") +
                " \n---------------------------";
    }
}
