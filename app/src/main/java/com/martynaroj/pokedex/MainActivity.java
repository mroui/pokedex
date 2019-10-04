package com.martynaroj.pokedex;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.martynaroj.pokedex.fragments.PokemonListFragment;
import com.martynaroj.pokedex.interfaces.NavigationListener;
import com.martynaroj.pokedex.retrofit.Rest;


public class MainActivity extends AppCompatActivity implements NavigationListener {

    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.doubleBackToExitPressedOnce = false;

        Rest.init();
        changeFragment(PokemonListFragment.newInstance(), false);
    }

//========================================

    @Override
    public void changeFragment(Fragment fragment, Boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                android.R.animator.fade_in,
                android.R.animator.fade_out,
                android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragmentTransaction.add(R.id.root, fragment);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

//========================================

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0)
            fragmentManager.popBackStack();
        else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
}
