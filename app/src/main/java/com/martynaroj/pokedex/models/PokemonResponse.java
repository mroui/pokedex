package com.martynaroj.pokedex.models;


import com.google.gson.annotations.SerializedName;
import java.util.List;


public class PokemonResponse {

    @SerializedName("count")
    private Integer count;

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private Boolean previous;

    @SerializedName("results")
    private List<PokemonUrl> results;


    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public Boolean getPrevious() {
        return previous;
    }

    public List<PokemonUrl> getResults() {
        return results;
    }
}