package com.alimparkar.themoviedb.network;

import com.alimparkar.themoviedb.network.exeception.NoNetworkFoundException;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by alimparkar on 23/04/18.
 */

public class RequestInterceptor implements Interceptor {

    private final ConnectivityHelper connectivityHelper;

    public RequestInterceptor(ConnectivityHelper connectivityHelper) {
        this.connectivityHelper = connectivityHelper;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!connectivityHelper.isOnline()) {
            throw new NoNetworkFoundException();
        }

        return chain.proceed(request);
    }
}
