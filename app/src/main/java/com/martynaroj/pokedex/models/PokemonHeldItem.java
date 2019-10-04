package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class PokemonHeldItem {

    @SerializedName("item")
    private PokemonUrl item;

    @SerializedName("version_details")
    private List<PokemonHeldItemVersion> version_details;


    public PokemonUrl getItem() {
        return item;
    }

    public List<PokemonHeldItemVersion> getVersion_details() {
        return version_details;
    }
}
