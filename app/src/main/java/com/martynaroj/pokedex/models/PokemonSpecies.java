package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

public class PokemonSpecies {

    @SerializedName("id")
    private Integer id;

    @SerializedName("evolution_chain")
    private PokemonUrl evolution_chain;


    public PokemonUrl getEvolution_chain() {
        return evolution_chain;
    }
}
