package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

class GenerationGameIndex {

    @SerializedName("game_index")
    private Integer game_index;

    @SerializedName("generation")
    private PokemonUrl generation;


    public Integer getGame_index() {
        return game_index;
    }

    public PokemonUrl getGeneration() {
        return generation;
    }
}
