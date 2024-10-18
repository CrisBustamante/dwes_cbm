package org.dwescbm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PokemonCenter {

    @JsonProperty("pokemons")
    @JacksonXmlElementWrapper(localName = "pokemons")
    @JacksonXmlProperty(localName = "pokemon")
    private List<Pokemon> pokemonList = new ArrayList<>();
}
