package org.dwescbm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@JacksonXmlRootElement(localName = "animal")
public class Animal {

    public enum AnimalSex {
        Macho, Hembra
    }

    @JacksonXmlProperty(localName = "id")
    private int id;

    @JacksonXmlProperty(localName = "nombre")
    private String name;

    @JacksonXmlProperty(localName = "especie")
    private String species;

    @JacksonXmlProperty(localName = "edad")
    private int age;

    @JacksonXmlProperty(localName = "sexo")
    private AnimalSex sex;

    @JacksonXmlProperty(localName = "fechaIngreso")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate entryDate;

    @JacksonXmlProperty(localName = "adoptado")
    private boolean adopted;

    @Override
    public String toString() {
        return "ID: " + id +
                " \nNOMBRE: " + name +
                " \nESPECIE: " + species +
                " \nEDAD: " + age +
                " \nSEXO: " + sex +
                " \nFECHA DE INGRESO: " + entryDate +
                " \nESTA ADOPTADO: " + adopted +
                " \n---------------------------";
    }
}

