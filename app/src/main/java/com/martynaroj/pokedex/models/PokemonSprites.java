package com.martynaroj.pokedex.models;

import com.google.gson.annotations.SerializedName;

public class PokemonSprites {

    @SerializedName("front_default")
    private String front_default;

    @SerializedName("front_shiny")
    private String front_shiny;

    @SerializedName("front_female")
    private String front_female;

    @SerializedName("front_shiny_female")
    private String front_shiny_female;

    @SerializedName("back_default")
    private String back_default;

    @SerializedName("back_shiny")
    private String back_shiny;

    @SerializedName("back_female")
    private String back_female;

    @SerializedName("back_shiny_female")
    private String back_shiny_female;


    public String getFront_default() {
        return front_default;
    }

    public String getFront_shiny() {
        return front_shiny;
    }

    public String getFront_female() {
        return front_female;
    }

    public String getFront_shiny_female() {
        return front_shiny_female;
    }

    public String getBack_default() {
        return back_default;
    }

    public String getBack_shiny() {
        return back_shiny;
    }

    public String getBack_female() {
        return back_female;
    }

    public String getBack_shiny_female() {
        return back_shiny_female;
    }
}
