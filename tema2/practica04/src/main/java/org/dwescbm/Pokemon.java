package org.dwescbm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {
    private String name;
    private String url;

    @Override
    public String toString() {
        return "Pokemon { " +
                "NOMBRE: " + name + " | " +
                "URL: " + url +
                " }";
    }
}
