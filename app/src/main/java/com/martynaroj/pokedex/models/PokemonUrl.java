package com.martynaroj.pokedex.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class PokemonUrl implements Serializable {

    @SerializedName("number")
    private Integer number;

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;


    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Integer getNumber() {
        String[] urlItems = url.split("/");
        return Integer.parseInt(urlItems[urlItems.length -1]);
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
