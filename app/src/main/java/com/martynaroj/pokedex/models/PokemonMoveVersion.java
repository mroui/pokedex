package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

class PokemonMoveVersion {

    @SerializedName("move_learn_method")
    private PokemonUrl move_learn_method;

    @SerializedName("version_group")
    private PokemonUrl version_group;

    @SerializedName("level_learned_at")
    private Integer level_learned_at;


    public PokemonUrl getMove_learn_method() {
        return move_learn_method;
    }

    public PokemonUrl getVersion_group() {
        return version_group;
    }

    public Integer getLevel_learned_at() {
        return level_learned_at;
    }
}
