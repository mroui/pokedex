package com.martynaroj.pokedex.fragments;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.martynaroj.pokedex.R;
import com.martynaroj.pokedex.models.ChainLink;
import com.martynaroj.pokedex.models.Pokemon;
import com.martynaroj.pokedex.models.PokemonTypeDetails;
import com.martynaroj.pokedex.models.PokemonUrl;
import com.martynaroj.pokedex.models.EvolutionChain;
import com.martynaroj.pokedex.models.PokemonSpecies;
import com.martynaroj.pokedex.retrofit.Rest;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PokemonDetails extends Fragment {

    private TextView pokemonName;
    private ImageView pokemonImageView;
    private TextView pokemonHeight, pokemonWeight, pokemonBaseExp, pokemonHp;
    private TextView pokemonSpeed, pokemonSpecialDefense, pokemonSpecialAttack, pokemonAttack, pokemonDefense;
    private LinearLayout layout_pokemon_types_imgaes, layout_pokemon_types_desc;
    private LinearLayout layout_pokemon_doubledamage_to, layout_pokemon_doubledamage_from;
    private LinearLayout layout_pokemon_nodamage_to, layout_pokemon_nodamage_from;
    private TextView doubledamageto, doubledamagefrom, nodamageto, nodamagefrom;
    private LinearLayout layout_pokemon_abilities;
    private ImageView imageEvolution1, imageEvolution2, imageEvolution3;
    private TextView nameEvolution1, nameEvolution2, nameEvolution3;

    private ConstraintLayout rootLayout;

    private AVLoadingIndicatorView avLoadingIndicatorView;

    private static final String ARG = "ARG";

    private PokemonUrl pokemonUrl;
    private PokemonUrl pokemonEvolution1, pokemonEvolution2, pokemonEvolution3;

    private Pokemon pokemon;
    private List<PokemonTypeDetails> pokemonTypeDetailsList;
    private boolean isCreated;
    private int typesCount;


    public PokemonDetails() {
        pokemon = new Pokemon();
        pokemonTypeDetailsList = new ArrayList<>();
        isCreated = false;
        typesCount = 1;
    }


//====================================

    public static PokemonDetails newInstance(PokemonUrl pokemonUrl) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG, pokemonUrl);
        PokemonDetails fragment = new PokemonDetails();
        fragment.setArguments(bundle);
        return fragment;
    }

//========================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            pokemonUrl = (PokemonUrl) getArguments().getSerializable("ARG");
    }

//========================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokemon_details, container, false);

        findViews(rootView);
        avLoadingIndicatorView.show();
        rootLayout.setVisibility(View.INVISIBLE);
        fetchData();

        return rootView;
    }

//========================================

    private void fetchData() {
        Rest.getRest().getPokemon(pokemonUrl.getNumber()).enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pokemon = response.body();
                    fetchDataTypes();
                }
            }
            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) { }
        });
    }

//========================================

    private void fetchDataTypes() {
        for(int i=0; i<pokemon.getTypes().size(); i++) {
            Rest.getRest().getPokemonType(pokemon.getTypes().get(i).getType().getNumber()).enqueue(new Callback<PokemonTypeDetails>() {
                @Override
                public void onResponse(Call<PokemonTypeDetails> call, Response<PokemonTypeDetails> response) {
                    if (response.isSuccessful() && response.body()!=null) {
                        pokemonTypeDetailsList.add(response.body());
                        fetchDataEvolution();
                    }
                }
                @Override
                public void onFailure(Call<PokemonTypeDetails> call, Throwable t) { }
            });
        }
    }

//========================================

    private void fetchDataEvolution() {
        Rest.getRest().getPokemonEvolution(pokemon.getSpecies().getNumber()).enqueue(new Callback<PokemonSpecies>() {
            @Override
            public void onResponse(Call<PokemonSpecies> call, Response<PokemonSpecies> response) {
                if (response.isSuccessful() && response.body()!=null) {

                    PokemonSpecies pokemonSpecies = response.body();

                    Rest.getRest().getPokemonEvolutionChain(pokemonSpecies.getEvolution_chain().getNumber()).enqueue(new Callback<EvolutionChain>() {
                        @Override
                        public void onResponse(Call<EvolutionChain> call, Response<EvolutionChain> response) {
                            if (response.isSuccessful() && response.body() != null) {

                                EvolutionChain evolutionChain = response.body();

                                pokemonEvolution1 = evolutionChain.getChain().getSpecies();

                                if(evolutionChain.getChain().getEvolves_to().size()>0) {

                                    ChainLink chainLink = evolutionChain.getChain().getEvolves_to().get(0);
                                    pokemonEvolution2 = chainLink.getSpecies();

                                    if (chainLink.getEvolves_to().size()>0) {
                                        pokemonEvolution3 = chainLink.getEvolves_to().get(0).getSpecies();
                                    }

                                }
                                setViewComponents();
                            }
                        }
                        @Override
                        public void onFailure(Call<EvolutionChain> call, Throwable t) { }
                    });

                }
            }
            @Override
            public void onFailure(Call<PokemonSpecies> call, Throwable t) { }
        });
    }

//========================================

    @SuppressLint("SetTextI18n")
    private void setViewComponents() {
        if (!isCreated) {
            pokemonName.setText(pokemon.getName());
            Glide.with(Objects.requireNonNull(getContext()))
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonUrl.getNumber() + ".png")
                    .fitCenter()
                    .error(R.drawable.emptyimage)
                    .into(pokemonImageView);

            double pHeight = (double) pokemon.getHeight() / 10.0;
            double pWeight = (double) pokemon.getWeight() / 10.0;
            pokemonHeight.setText(pHeight + "m");
            pokemonBaseExp.setText(pokemon.getBase_experience().toString());
            pokemonWeight.setText(pWeight + "kg");

            pokemonSpeed.setText(pokemon.getStats().get(0).getBase_stat().toString());
            pokemonSpecialDefense.setText(pokemon.getStats().get(1).getBase_stat().toString());
            pokemonSpecialAttack.setText(pokemon.getStats().get(2).getBase_stat().toString());
            pokemonDefense.setText(pokemon.getStats().get(3).getBase_stat().toString());
            pokemonAttack.setText(pokemon.getStats().get(4).getBase_stat().toString());
            pokemonHp.setText(pokemon.getStats().get(5).getBase_stat().toString());

            for (int i = 0; i < pokemon.getTypes().size(); i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(getResources().getIdentifier("type" + pokemon.getTypes().get(i).getType().getNumber().toString(), "drawable", getContext().getPackageName()));
                layout_pokemon_types_imgaes.addView(imageView);
                imageView.getLayoutParams().height = 120;
                imageView.getLayoutParams().width = 120;
                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.textview_layout, null);
                if (i + 1 < pokemon.getTypes().size())
                    textView.setText(pokemon.getTypes().get(i).getType().getName().toUpperCase() + " / ");
                else
                    textView.setText(pokemon.getTypes().get(i).getType().getName().toUpperCase());
                layout_pokemon_types_desc.addView(textView);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            params.rightMargin = 16;
            params.leftMargin = 16;
            for(int i=0; i<pokemon.getAbilities().size(); i++) {
                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.textview_layout, null);
                textView.setText(pokemon.getAbilities().get(i).getAbility().getName());
                textView.setTextColor(Color.WHITE);
                textView.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                textView.setPadding(16,16,16,16);
                textView.setLayoutParams(params);
                layout_pokemon_abilities.addView(textView);
            }

            final String basicImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

            if(pokemonEvolution1!=null) {
                Glide.with(Objects.requireNonNull(getContext()))
                        .load(basicImageUrl + pokemonEvolution1.getNumber() + ".png")
                        .fitCenter()
                        .into(imageEvolution1);
                nameEvolution1.setText(pokemonEvolution1.getName());
            } else {
                imageEvolution1.setVisibility(View.GONE);
                nameEvolution1.setVisibility(View.GONE);
            }
            if(pokemonEvolution2!=null) {
                Glide.with(Objects.requireNonNull(getContext()))
                        .load(basicImageUrl + pokemonEvolution2.getNumber() + ".png")
                        .fitCenter()
                        .into(imageEvolution2);
                nameEvolution2.setText(pokemonEvolution2.getName());
            } else {
                imageEvolution2.setVisibility(View.GONE);
                nameEvolution2.setVisibility(View.GONE);
            }
            if(pokemonEvolution3!=null) {
                Glide.with(Objects.requireNonNull(getContext()))
                        .load(basicImageUrl + pokemonEvolution3.getNumber() + ".png")
                        .fitCenter()
                        .into(imageEvolution3);
                nameEvolution3.setText(pokemonEvolution3.getName());
            } else {
                imageEvolution3.setVisibility(View.GONE);
                nameEvolution3.setVisibility(View.GONE);
            }

        isCreated = true;
        }

        if (typesCount == pokemon.getTypes().size()) {
            for (int i = 0; i < pokemonTypeDetailsList.size(); i++) {

                for (int j = 0; j < pokemonTypeDetailsList.get(i).getDamage_relations().getDouble_damage_to().size(); j++) {
                    Integer number = pokemonTypeDetailsList.get(i).getDamage_relations().getDouble_damage_to().get(j).getNumber();
                    ImageView imageView = setImageView(number);
                    layout_pokemon_doubledamage_to.addView(imageView);
                    doubledamageto.setVisibility(View.GONE);
                }

                for (int j = 0; j < pokemonTypeDetailsList.get(i).getDamage_relations().getDouble_damage_from().size(); j++) {
                    Integer number = pokemonTypeDetailsList.get(i).getDamage_relations().getDouble_damage_from().get(j).getNumber();
                    ImageView imageView = setImageView(number);
                    layout_pokemon_doubledamage_from.addView(imageView);
                    doubledamagefrom.setVisibility(View.GONE);
                }

                for (int j = 0; j < pokemonTypeDetailsList.get(i).getDamage_relations().getNo_damage_to().size(); j++) {
                    Integer number = pokemonTypeDetailsList.get(i).getDamage_relations().getNo_damage_to().get(j).getNumber();
                    ImageView imageView = setImageView(number);
                    layout_pokemon_nodamage_to.addView(imageView);
                    nodamageto.setVisibility(View.GONE);
                }

                for (int j = 0; j < pokemonTypeDetailsList.get(i).getDamage_relations().getNo_damage_from().size(); j++) {
                    Integer number = pokemonTypeDetailsList.get(i).getDamage_relations().getNo_damage_from().get(j).getNumber();
                    ImageView imageView = setImageView(number);
                    layout_pokemon_nodamage_from.addView(imageView);
                    nodamagefrom.setVisibility(View.GONE);
                }

                if (i + 1 < pokemonTypeDetailsList.size()) {
                    TextView textView;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER_VERTICAL;

                    if (pokemonTypeDetailsList.get(i).getDamage_relations().getDouble_damage_to().size()>0 &&
                            pokemonTypeDetailsList.get(i+1).getDamage_relations().getDouble_damage_to().size()>0) {
                        textView = (TextView) getLayoutInflater().inflate(R.layout.textview_layout, null);
                        textView.setText(" / ");
                        textView.setLayoutParams(params);
                        layout_pokemon_doubledamage_to.addView(textView);
                    }
                    if (pokemonTypeDetailsList.get(i).getDamage_relations().getDouble_damage_from().size()>0 &&
                            pokemonTypeDetailsList.get(i+1).getDamage_relations().getDouble_damage_from().size()>0) {
                        textView = (TextView) getLayoutInflater().inflate(R.layout.textview_layout, null);
                        textView.setText(" / ");
                        textView.setLayoutParams(params);
                        layout_pokemon_doubledamage_from.addView(textView);
                    }
                    if (pokemonTypeDetailsList.get(i).getDamage_relations().getNo_damage_to().size()>0 &&
                            pokemonTypeDetailsList.get(i+1).getDamage_relations().getNo_damage_to().size()>0) {
                        textView = (TextView) getLayoutInflater().inflate(R.layout.textview_layout, null);
                        textView.setText(" / ");
                        textView.setLayoutParams(params);
                        layout_pokemon_nodamage_to.addView(textView);
                    }
                    if (pokemonTypeDetailsList.get(i).getDamage_relations().getNo_damage_from().size()>0 &&
                            pokemonTypeDetailsList.get(i+1).getDamage_relations().getNo_damage_from().size()>0) {
                        textView = (TextView) getLayoutInflater().inflate(R.layout.textview_layout, null);
                        textView.setText(" / ");
                        textView.setLayoutParams(params);
                        layout_pokemon_nodamage_from.addView(textView);
                    }
                }
            }
            avLoadingIndicatorView.hide();
            rootLayout.setVisibility(View.VISIBLE);
        } else
            typesCount++;
    }

//========================================

    private ImageView setImageView(Integer number) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        params.height = 100;
        params.width = 100;
        params.gravity = Gravity.CENTER_VERTICAL;
        imageView.setLayoutParams(params);
        imageView.setImageResource(getResources().getIdentifier("type" + number.toString(),
                "drawable", Objects.requireNonNull(getContext()).getPackageName()));
        return imageView;
    }

//========================================

    private void findViews(View rootView) {
        rootLayout = rootView.findViewById(R.id.rootLayout);
        avLoadingIndicatorView = rootView.findViewById(R.id.avLoadingIndicatorView);
        pokemonName = rootView.findViewById(R.id.pokemon_details_name);
        pokemonImageView = rootView.findViewById(R.id.pokemon_details_image);
        pokemonHeight = rootView.findViewById(R.id.pokemon_height);
        pokemonBaseExp = rootView.findViewById(R.id.pokemon_base_exp);
        pokemonWeight = rootView.findViewById(R.id.pokemon_weight);
        pokemonHp = rootView.findViewById(R.id.pokemon_hp);
        pokemonSpeed = rootView.findViewById(R.id.pokemon_speed);
        pokemonAttack = rootView.findViewById(R.id.pokemon_attack);
        pokemonDefense = rootView.findViewById(R.id.pokemon_defense);
        pokemonSpecialDefense = rootView.findViewById(R.id.pokemon_special_defense);
        pokemonSpecialAttack = rootView.findViewById(R.id.pokemon_special_attack);
        layout_pokemon_types_imgaes = rootView.findViewById(R.id.pokemon_types_list_image);
        layout_pokemon_types_desc = rootView.findViewById(R.id.pokemon_types_list_desc);
        layout_pokemon_doubledamage_to = rootView.findViewById(R.id.layout_pokemon_doubledamage_to);
        layout_pokemon_doubledamage_from = rootView.findViewById(R.id.layout_pokemon_doubledamage_from);
        layout_pokemon_nodamage_to = rootView.findViewById(R.id.layout_pokemon_nodamage_to);
        layout_pokemon_nodamage_from = rootView.findViewById(R.id.layout_pokemon_nodamage_from);
        doubledamagefrom = rootView.findViewById(R.id.none_doubledamage_from);
        doubledamageto = rootView.findViewById(R.id.none_doubledamage_to);
        nodamagefrom = rootView.findViewById(R.id.none_nodamage_from);
        nodamageto = rootView.findViewById(R.id.none_nodamage_to);
        layout_pokemon_abilities = rootView.findViewById(R.id.pokemon_abilities);
        imageEvolution1 = rootView.findViewById(R.id.pokemon_evolution_1);
        imageEvolution2 = rootView.findViewById(R.id.pokemon_evolution_2);
        imageEvolution3 = rootView.findViewById(R.id.pokemon_evolution_3);
        nameEvolution1 = rootView.findViewById(R.id.pokemon_evolution_1_desc);
        nameEvolution2 = rootView.findViewById(R.id.pokemon_evolution_2_desc);
        nameEvolution3 = rootView.findViewById(R.id.pokemon_evolution_3_desc);
    }
}
