package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

class TypePokemon {

    @SerializedName("slot")
    private Integer slot;

    @SerializedName("pokemon")
    private PokemonUrl pokemon;


    public Integer getSlot() {
        return slot;
    }

    public PokemonUrl getPokemon() {
        return pokemon;
    }
}
