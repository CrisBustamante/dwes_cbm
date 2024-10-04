package org.dwescbm;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;

public class XMLOperations {
    private static final String XML_FILE = "src/main/resources/animals.xml";
    private XmlMapper xmlMapper = new XmlMapper();

    public AnimalShelter loadXML() throws IOException {
        File file = new File(XML_FILE);
        if (file.exists()) {
            return xmlMapper.readValue(file, AnimalShelter.class);
        } else {
            return new AnimalShelter();
        }
    }

    public void saveXML(AnimalShelter shelter) throws IOException {
        xmlMapper.writeValue(new File(XML_FILE), shelter);
    }
}
