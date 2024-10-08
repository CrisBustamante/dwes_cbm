package org.dwescbm;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter

@JacksonXmlRootElement(localName = "protectoraDeAnimales")
public class AnimalShelter {

    @JacksonXmlElementWrapper(localName = "animales")
    @JacksonXmlProperty(localName = "animal")
    private List<Animal> animalList = new ArrayList<>();
}