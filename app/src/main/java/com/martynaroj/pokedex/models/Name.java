package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

class Name {

    @SerializedName("name")
    private String name;

    @SerializedName("language")
    private PokemonUrl language;


    public String getName() {
        return name;
    }

    public PokemonUrl getLanguage() {
        return language;
    }
}
