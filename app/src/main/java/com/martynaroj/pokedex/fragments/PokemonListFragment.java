package com.martynaroj.pokedex.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.martynaroj.pokedex.R;
import com.martynaroj.pokedex.base.BaseFragment;
import com.martynaroj.pokedex.fragments.adapters.PokemonListAdapter;
import com.martynaroj.pokedex.interfaces.NetworkListener;
import com.martynaroj.pokedex.interfaces.OnItemListener;
import com.martynaroj.pokedex.models.PokemonResponse;
import com.martynaroj.pokedex.models.PokemonUrl;
import com.martynaroj.pokedex.retrofit.Rest;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PokemonListFragment extends BaseFragment implements OnItemListener, NetworkListener {

    private RecyclerView recyclerView;
    private List<PokemonUrl> pokemonsList;
    private PokemonListAdapter adapter;
    private SearchView searchBar;

    private Integer offset;
    private Integer limit;

    private boolean loading;
    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal;
    private boolean root;


    public static PokemonListFragment newInstance() {
        return new PokemonListFragment();
    }

//========================================

    public PokemonListFragment() {
        pokemonsList = new ArrayList<>();
        previousTotal = 0;
        offset = 0;
        limit = 21;
        loading = true;
        root = true;
    }

//========================================

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        findViews(rootView);
        setSearchBar();
        setAdapter();
        fetchData(null);

        return rootView;
    }

//========================================

    private void setSearchBar() {
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setIconified(false);
            }
        });
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s==null || s.equals("")) {
                    root = true;
                    offset = 0;
                    limit = 21;
                }
                else {
                    root = false;
                    offset = 0;
                    limit = 964;
                }
                recyclerView.scrollTo(0,0);
                adapter.clear();
                fetchData(s);
                return false;
            }
        });
    }

//========================================

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

//========================================

    private void fetchData(final String s) {
        if (isNetworkConnected(getContext())) {
            pokemonsList.clear();
            Rest.getRest().getListPokemons(String.valueOf(offset), String.valueOf(limit)).enqueue(new Callback<PokemonResponse>() {
                @Override
                public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        pokemonsList.addAll(response.body().getResults());
                        adapter.addList(pokemonsList);
                        adapter.getFilter().filter(s);
                    }
                }

                @Override
                public void onFailure(Call<PokemonResponse> call, Throwable t) {
                }
            });
        } else {
            showNetworkAlert();
        }
    }

//========================================

    private void findViews(View rootView) {
        recyclerView = rootView.findViewById(R.id.list_pokemons_recyclerview);
        searchBar = rootView.findViewById(R.id.searchView);
    }

//========================================

    public void showNetworkAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialogBuilder
                .setMessage("No internet connection on your device.")
                .setTitle("No Internet Connection")
                .setCancelable(false)
                .setPositiveButton("Refresh",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {}
                        });
        final AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                if (!isNetworkConnected(getContext()))
                    alert.show();
                else
                    fetchData(null);
            }
        });
    }

//=====================================

    private void setAdapter() {
        adapter = new PokemonListAdapter(getContext(), this);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (!root)
                    previousTotal = 0;
                else {
                    if (loading && (totalItemCount > previousTotal)) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                    if (!loading && (visibleItemCount + firstVisibleItem) >= totalItemCount) {
                        offset += 21;
                        fetchData(null);
                        loading = true;
                    }
                }
            }
        });
    }

//=========================================

    @Override
    public void onItemClick(int position) {
        getNavigationsInteractions().changeFragment(PokemonDetails.newInstance(adapter.getPokemon(position)), true);
    }
}
