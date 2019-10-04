package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

public class PokemonType {

    @SerializedName("slot")
    private Integer slot;

    @SerializedName("type")
    private PokemonUrl type;


    public Integer getSlot() {
        return slot;
    }

    public PokemonUrl getType() {
        return type;
    }
}
