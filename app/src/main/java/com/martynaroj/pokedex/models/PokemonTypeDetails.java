package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonTypeDetails {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("damage_relations")
    private TypeRelations damage_relations;

    @SerializedName("game_indices")
    private List<GenerationGameIndex> game_indices;

    @SerializedName("generation")
    private PokemonUrl generation;

    @SerializedName("move_damage_class")
    private PokemonUrl move_damage_class;

    @SerializedName("names")
    private List<Name> names;

    @SerializedName("pokemon")
    private List<TypePokemon> pokemon;

    @SerializedName("moves")
    private List<PokemonUrl> moves;


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TypeRelations getDamage_relations() {
        return damage_relations;
    }

    public List<GenerationGameIndex> getGame_indices() {
        return game_indices;
    }

    public PokemonUrl getGeneration() {
        return generation;
    }

    public PokemonUrl getMove_damage_class() {
        return move_damage_class;
    }

    public List<Name> getNames() {
        return names;
    }

    public List<TypePokemon> getPokemon() {
        return pokemon;
    }

    public List<PokemonUrl> getMoves() {
        return moves;
    }
}
