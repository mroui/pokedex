package com.martynaroj.pokedex.retrofit;


import com.martynaroj.pokedex.models.EvolutionChain;
import com.martynaroj.pokedex.models.Pokemon;
import com.martynaroj.pokedex.models.PokemonResponse;
import com.martynaroj.pokedex.models.PokemonSpecies;
import com.martynaroj.pokedex.models.PokemonTypeDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RestInterface {

    @GET("pokemon")
    Call<PokemonResponse> getListPokemons(@Query("offset") String offset,
                                          @Query("limit") String limit);

    @GET("pokemon/{id}/")
    Call<Pokemon> getPokemon(@Path("id") Integer pokemonId);

    @GET("type/{id}/")
    Call<PokemonTypeDetails> getPokemonType(@Path("id") Integer typeId);

    @GET("pokemon-species/{id}/")
    Call<PokemonSpecies> getPokemonEvolution(@Path("id") Integer typeId);

    @GET("evolution-chain/{id}/")
    Call<EvolutionChain> getPokemonEvolutionChain(@Path("id") Integer typeId);
}
