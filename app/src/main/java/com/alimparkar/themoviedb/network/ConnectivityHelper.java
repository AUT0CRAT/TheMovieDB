package com.alimparkar.themoviedb.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import javax.inject.Inject;


public class ConnectivityHelper {

    private final ConnectivityManager connectivityManager;

    @Inject
    public ConnectivityHelper(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    @SuppressWarnings("WeakerAccess")
    public boolean isOnline() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }
}
