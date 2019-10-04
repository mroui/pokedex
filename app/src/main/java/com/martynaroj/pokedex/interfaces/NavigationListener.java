package com.martynaroj.pokedex.interfaces;


import android.support.v4.app.Fragment;


public interface NavigationListener {

    void changeFragment(Fragment fragment, Boolean addToBackStack);

}