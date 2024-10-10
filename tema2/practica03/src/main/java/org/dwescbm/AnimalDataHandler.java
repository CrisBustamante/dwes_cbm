package org.dwescbm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.nio.file.Path;

public class AnimalDataHandler {

    private ObjectMapper jsonMapper;
    private XmlMapper xmlMapper;

    public AnimalDataHandler() {
        this.jsonMapper = new ObjectMapper();
        this.jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        this.xmlMapper = new XmlMapper();
        this.xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    // Cargar datos dependiendo del formato (JSON o XML)
    public AnimalShelter load(Path path) throws IOException {
        String fileName = path.getFileName().toString().toLowerCase();
        if (fileName.endsWith(".json")) {
            return jsonMapper.readValue(path.toFile(), new TypeReference<AnimalShelter>() {});
        } else if (fileName.endsWith(".xml")) {
            return xmlMapper.readValue(path.toFile(), new TypeReference<AnimalShelter>() {});
        } else {
            throw new IOException("Formato de archivo no soportado: " + fileName);
        }
    }

    // Guardar datos dependiendo del formato (JSON o XML)
    public void save(AnimalShelter animalShelter, Path path) throws IOException {
        String fileName = path.getFileName().toString().toLowerCase();
        if (fileName.endsWith(".json")) {
            jsonMapper.writeValue(path.toFile(), animalShelter);
        } else if (fileName.endsWith(".xml")) {
            xmlMapper.writeValue(path.toFile(), animalShelter);
        } else {
            throw new IOException("Formato de archivo no soportado: " + fileName);
        }
    }
}
