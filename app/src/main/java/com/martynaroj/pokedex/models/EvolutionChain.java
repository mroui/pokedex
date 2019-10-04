package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

public class EvolutionChain {

    @SerializedName("id")
    private Integer id;

    @SerializedName("baby_trigger_item")
    private PokemonUrl baby_trigger_item;

    @SerializedName("chain")
    private ChainLink chain;


    public Integer getId() {
        return id;
    }

    public PokemonUrl getBaby_trigger_item() {
        return baby_trigger_item;
    }

    public ChainLink getChain() {
        return chain;
    }
}
