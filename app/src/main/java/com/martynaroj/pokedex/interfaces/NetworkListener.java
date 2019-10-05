package com.martynaroj.pokedex.interfaces;

import android.content.Context;
import android.net.ConnectivityManager;

public interface NetworkListener {

    void showNetworkAlert();

    default boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
