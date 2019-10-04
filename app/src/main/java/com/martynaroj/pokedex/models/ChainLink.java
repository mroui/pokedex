package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChainLink {

    @SerializedName("is_baby")
    private boolean is_baby;

    @SerializedName("species")
    private PokemonUrl species;

    @SerializedName("evolves_to")
    private List<ChainLink> evolves_to;


    public boolean isIs_baby() {
        return is_baby;
    }

    public PokemonUrl getSpecies() {
        return species;
    }

    public List<ChainLink> getEvolves_to() {
        return evolves_to;
    }
}
