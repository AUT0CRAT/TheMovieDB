package com.alimparkar.themoviedb.network.exeception;

/**
 * Created by alimparkar on 23/04/18.
 */

public class NoNetworkFoundException extends RuntimeException {
    public NoNetworkFoundException() {
        super("No Internet connectivity");
    }
}
