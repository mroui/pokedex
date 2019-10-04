package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

class VersionGameIndex {

    @SerializedName("game_index")
    private Integer game_index;

    @SerializedName("version")
    private PokemonUrl version;


    public Integer getGame_index() {
        return game_index;
    }

    public PokemonUrl getVersion() {
        return version;
    }
}
