package com.martynaroj.pokedex.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Pokemon {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("base_experience")
    private Integer base_experience;

    @SerializedName("height")
    private Integer height;

    @SerializedName("is_default")
    private Boolean is_default;

    @SerializedName("order")
    private Integer order;

    @SerializedName("weight")
    private Integer weight;

    @SerializedName("abilities")
    private List<PokemonAbility> abilities;

    @SerializedName("forms")
    private List<PokemonUrl> forms;

    @SerializedName("game_indices")
    private List<VersionGameIndex> game_indices;

    @SerializedName("held_items")
    private List<PokemonHeldItem> held_items;

    @SerializedName("location_area_encounters")
    private String location_area_encounters;

    @SerializedName("moves")
    private List<PokemonMove> moves;

    @SerializedName("sprites")
    private PokemonSprites sprites;

    @SerializedName("species")
    private PokemonUrl species;

    @SerializedName("stats")
    private List<PokemonStat> stats;

    @SerializedName("types")
    private List<PokemonType> types;


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBase_experience() {
        return base_experience;
    }

    public Integer getHeight() {
        return height;
    }

    public Boolean getIs_default() {
        return is_default;
    }

    public Integer getOrder() {
        return order;
    }

    public Integer getWeight() {
        return weight;
    }

    public List<PokemonAbility> getAbilities() {
        return abilities;
    }

    public List<PokemonUrl> getForms() {
        return forms;
    }

    public List<VersionGameIndex> getGame_indices() {
        return game_indices;
    }

    public List<PokemonHeldItem> getHeld_items() {
        return held_items;
    }

    public String getLocation_area_encounters() {
        return location_area_encounters;
    }

    public List<PokemonMove> getMoves() {
        return moves;
    }

    public PokemonSprites getSprites() {
        return sprites;
    }

    public PokemonUrl getSpecies() {
        return species;
    }

    public List<PokemonStat> getStats() {
        return stats;
    }

    public List<PokemonType> getTypes() {
        return types;
    }
}
