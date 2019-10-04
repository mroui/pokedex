package com.martynaroj.pokedex.models;


import com.google.gson.annotations.SerializedName;


public class PokemonStat {

    @SerializedName("stat")
    private PokemonUrl stat;

    @SerializedName("effort")
    private Integer effort;

    @SerializedName("base_stat")
    private Integer base_stat;


    public PokemonUrl getStat() {
        return stat;
    }

    public Integer getEffort() {
        return effort;
    }

    public Integer getBase_stat() {
        return base_stat;
    }
}
