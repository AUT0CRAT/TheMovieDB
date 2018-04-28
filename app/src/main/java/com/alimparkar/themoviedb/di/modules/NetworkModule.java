package com.alimparkar.themoviedb.di.modules;

import android.content.Context;
import android.net.ConnectivityManager;
import com.alimparkar.themoviedb.BuildConfig;
import com.alimparkar.themoviedb.network.ApiService;
import com.alimparkar.themoviedb.network.ConnectivityHelper;
import com.alimparkar.themoviedb.network.RequestInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alimparkar on 23/04/18.
 */
@Module
public class NetworkModule {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    /**
     * utility to create RestAdapter instances
     *
     * @return RestApi interface
     */
    @Provides
    public ApiService provideApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    /**
     * @return The Retrofit adapter instance which can be used to create
     * the Service instance for making HTTP requests.
     */
    @Provides
    public Retrofit provideRetrofit(OkHttpClient httpClient, GsonConverterFactory converterFactory,
        CallAdapter.Factory rxErrorHandler) {

        return new Retrofit.Builder().client(httpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(rxErrorHandler)
            .addConverterFactory(converterFactory)
            .build();
    }

    @Provides
    public CallAdapter.Factory provideCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    public GsonConverterFactory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public OkHttpClient provideOkHttpClient(@Named("logger") Interceptor logger,
        @Named("request") Interceptor sessionRequestInterceptor) {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().followSslRedirects(true)
            .followRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            // allow HTTP2 if server supports it
            .protocols(Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2))
            // interceptors - order is important
            .addInterceptor(sessionRequestInterceptor)
            .addInterceptor(logger);

        return httpClientBuilder.build();
    }

    @Provides
    @Named("logger")
    public Interceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
            : HttpLoggingInterceptor.Level.NONE);

        return loggingInterceptor;
    }

    @Provides
    @Singleton
    @Named("request")
    public Interceptor provideSessionRequestInterceptor(ConnectivityHelper helper) {
        return new RequestInterceptor(helper);
    }

    @Provides
    public ConnectivityHelper provideConnectivityHelper(ConnectivityManager connectivityManager) {
        return new ConnectivityHelper(connectivityManager);
    }

    @Provides
    public ConnectivityManager provideConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * Default Gson builder object.
     *
     * @return Gson
     */
    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
            //.registerTypeAdapterFactory(new JsonTypeAdapterFactory())
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    @Provides
    @Named("apikey")
    public String provideApiKey() {
        return BuildConfig.API_KEY;
    }
}
