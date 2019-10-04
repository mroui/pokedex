package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

class PokemonHeldItemVersion {

    @SerializedName("version")
    private PokemonUrl version;

    @SerializedName("rarity")
    private Integer rarity;


    public PokemonUrl getVersion() {
        return version;
    }

    public Integer getRarity() {
        return rarity;
    }
}
