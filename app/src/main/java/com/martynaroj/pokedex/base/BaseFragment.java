package com.martynaroj.pokedex.base;


import android.content.Context;
import android.support.v4.app.Fragment;

import com.martynaroj.pokedex.interfaces.NavigationListener;


public class BaseFragment extends Fragment {

    private NavigationListener navigationListener;


    public NavigationListener getNavigationsInteractions() {
        return navigationListener;
    }

//=========================================

    @Override
    public void onDetach() {
        super.onDetach();
        navigationListener = null;
    }

//=========================================

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationListener)
            navigationListener = (NavigationListener) context;
    }
}

