package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

public class PokemonAbility {

    @SerializedName("is_hidden")
    private Boolean is_hidden;

    @SerializedName("slot")
    private Integer slot;

    @SerializedName("ability")
    private PokemonUrl ability;


    public Boolean getIs_hidden() {
        return is_hidden;
    }

    public Integer getSlot() {
        return slot;
    }

    public PokemonUrl getAbility() {
        return ability;
    }
}
