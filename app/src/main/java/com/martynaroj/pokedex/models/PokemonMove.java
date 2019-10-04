package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class PokemonMove {

    @SerializedName("move")
    private PokemonUrl move;

    @SerializedName("version_group_details")
    private List<PokemonMoveVersion> version_group_details;


    public PokemonUrl getMove() {
        return move;
    }

    public List<PokemonMoveVersion> getVersion_group_details() {
        return version_group_details;
    }
}
