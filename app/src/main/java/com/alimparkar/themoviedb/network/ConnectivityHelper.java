package com.alimparkar.themoviedb.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by alimparkar on 23/04/18.
 */

public class ConnectivityHelper {

    private ConnectivityManager connectivityManager;

    public ConnectivityHelper(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

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
