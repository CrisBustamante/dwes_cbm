package org.dwescbm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Ability {
    private String name;
    private String url;

    @Override
    public String toString() {
        return "    Habilidad { " +
                "NOMBRE: " + name + " | " +
                "URL: " + url + " | " +
                " }";
    }
}