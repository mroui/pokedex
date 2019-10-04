package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypeRelations {

    @SerializedName("name")
    private PokemonUrl name;

    @SerializedName("no_damage_to")
    private List<PokemonUrl> no_damage_to;

    @SerializedName("half_damage_to")
    private List<PokemonUrl> half_damage_to;

    @SerializedName("double_damage_to")
    private List<PokemonUrl> double_damage_to;

    @SerializedName("no_damage_from")
    private List<PokemonUrl> no_damage_from;

    @SerializedName("half_damage_from")
    private List<PokemonUrl> half_damage_from;

    @SerializedName("double_damage_from")
    private List<PokemonUrl> double_damage_from;


    public PokemonUrl getName() {
        return name;
    }

    public List<PokemonUrl> getNo_damage_to() {
        return no_damage_to;
    }

    public List<PokemonUrl> getHalf_damage_to() {
        return half_damage_to;
    }

    public List<PokemonUrl> getDouble_damage_to() {
        return double_damage_to;
    }

    public List<PokemonUrl> getNo_damage_from() {
        return no_damage_from;
    }

    public List<PokemonUrl> getHalf_damage_from() {
        return half_damage_from;
    }

    public List<PokemonUrl> getDouble_damage_from() {
        return double_damage_from;
    }
}
