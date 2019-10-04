package com.martynaroj.pokedex.fragments.adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.martynaroj.pokedex.R;
import com.martynaroj.pokedex.interfaces.OnItemListener;
import com.martynaroj.pokedex.models.PokemonUrl;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder> implements Filterable {

    private List<PokemonUrl> pokemonList = new ArrayList<>();
    private List<PokemonUrl> pokemonListTemp;
    private Context context;
    private OnItemListener onItemListener;


    public PokemonListAdapter(Context context, OnItemListener onItemListener) {
        this.context = context;
        this.onItemListener = onItemListener;
    }

//========================================

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pokemon, viewGroup, false);
        return new PokemonViewHolder(itemView, onItemListener);
    }

//========================================

    @Override
    public int getItemCount() {
        if (pokemonListTemp != null) return pokemonListTemp.size();
        else return 0;
    }

//========================================

    @Override
    public void onBindViewHolder(@NonNull final PokemonViewHolder pokemonViewHolder, int position) {
        PokemonUrl item = pokemonListTemp.get(position);
        pokemonViewHolder.name.setText(item.getName());

        Glide.with(this.context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + item.getNumber() + ".png")
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        pokemonViewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pokemonViewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.emptyimage)
                .into(pokemonViewHolder.img);
    }

//========================================

    public void addList(List<PokemonUrl> pokemonUrls) {
        pokemonList.addAll(pokemonUrls);
        pokemonListTemp = new ArrayList<>(pokemonList);
    }
//========================================

    public PokemonUrl getPokemon(int position) {
        return pokemonListTemp.get(position);
    }

//========================================

    @Override
    public Filter getFilter() {
        return filter;
    }

//========================================

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            pokemonListTemp = new ArrayList<>(pokemonList);
            List<PokemonUrl> filteredList = new ArrayList<>();
            if (constraint == null || constraint.equals("")) {
                filteredList.addAll(pokemonListTemp);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PokemonUrl pokemonUrl : pokemonListTemp) {
                    if (pokemonUrl.getName().toLowerCase().startsWith(filterPattern)) {
                        if (!containsInList(filteredList, pokemonUrl))
                            filteredList.add(pokemonUrl);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pokemonListTemp.clear();
            pokemonListTemp.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

//=========================================

    public void clear() {
        pokemonList.clear();
        notifyDataSetChanged();
    }


//=========================================

    private boolean containsInList(List<PokemonUrl> list, PokemonUrl pokemon) {
        for(PokemonUrl x : list) {
            if (x.getName().equals(pokemon.getName()))
                return true;
        }
        return false;
    }

//========================================

    class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView img;
        OnItemListener onItemListener;
        AVLoadingIndicatorView progressBar;


        PokemonViewHolder(View view, OnItemListener onItemListener) {
            super(view);

            name = view.findViewById(R.id.pokemon_name);
            img = view.findViewById(R.id.pokemon_img);
            progressBar = view.findViewById(R.id.progress_bar);

            progressBar.show();

            this.onItemListener = onItemListener;

            view.setOnClickListener(this);
        }

    //===========================================

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }

    }

}
