package com.alimparkar.themoviedb.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import com.alimparkar.themoviedb.models.Movie;
import com.alimparkar.themoviedb.models.TopMovieResponse;
import com.alimparkar.themoviedb.network.ApiService;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Named;
import timber.log.Timber;

/**
 * The data source for the movies. This class provides a paged data for the top movies. It will call
 * the getTopMovies api and fetch the topMovies for a given page
 */
public class MoviesDataSource extends PageKeyedDataSource<Integer, Movie> {
    private static final NetworkState LOADING = new NetworkState(NetworkState.STATUS.RUNNING, null);
    private static final NetworkState LOADED = new NetworkState(NetworkState.STATUS.SUCCESS, null);
    private final ApiService apiService;
    private final String apiKey;
    private final MutableLiveData<NetworkState> networkState;

    MoviesDataSource(ApiService apiService, @Named("apikey") String apiKey) {
        this.apiService = apiService;
        this.apiKey = apiKey;

        networkState = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
        @NonNull LoadInitialCallback<Integer, Movie> callback) {
        Timber.d("Initial load");
        networkState.postValue(LOADING);
        performInitialFetch(callback);
    }

    private void performInitialFetch(LoadInitialCallback<Integer, Movie> callback) {
        int page = 1;
        apiService.getTopMovies(apiKey, page)
            .map(TopMovieResponse::getResults)
            .subscribeOn(Schedulers.io())
            .subscribe(movies -> {
                Timber.d("Fetched initial movies for page: %s", page);
                callback.onResult(movies, null, page + 1);
                networkState.postValue(LOADED);
            }, error -> {
                Timber.d("Failed to Fetch inital movies for page: %s", page);
                NetworkState errorState =
                    new NetworkState(NetworkState.STATUS.FAILED, error.getMessage());
                networkState.postValue(errorState);
            });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
        @NonNull LoadCallback<Integer, Movie> callback) {
        Timber.d("Load before %s", params.key);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
        @NonNull LoadCallback<Integer, Movie> callback) {
        Timber.d("Load after %s", params.key);
        networkState.postValue(LOADING);
        performFetch(params.key, callback);
    }

    private void performFetch(int page, @NonNull LoadCallback<Integer, Movie> callback) {
        apiService.getTopMovies(apiKey, page)
            .map(TopMovieResponse::getResults)
            .subscribeOn(Schedulers.io())
            .subscribe(movies -> {
                Timber.d("Fetched movies for page: %s", page);
                callback.onResult(movies, page + 1);
                networkState.postValue(LOADED);
            }, error -> {
                Timber.d("Failed Fetched movies for page: %s", page);
                NetworkState errorState =
                    new NetworkState(NetworkState.STATUS.FAILED, error.getMessage());
                networkState.postValue(errorState);
            });
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
