package com.alimparkar.themoviedb.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import com.alimparkar.themoviedb.models.Movie;
import com.alimparkar.themoviedb.network.ApiService;
import javax.inject.Inject;
import javax.inject.Named;
import timber.log.Timber;

/**
 * DataSource factory to provide LiveData for the movies. This is attached to a DataSource. The
 * dataource will be used to backup the paged list
 */
public class MoviesDataSourceFactory extends DataSource.Factory<Integer, Movie> {


    private MutableLiveData<MoviesDataSource> sourceData;

    private ApiService apiService;
    private String apiKey;

    @Inject
    public MoviesDataSourceFactory(ApiService apiService, @Named("apikey") String apiKey) {
        this.apiService = apiService;
        this.apiKey = apiKey;
        sourceData = new MutableLiveData<>();
    }

    /**
     * DONOT INJECT. As we need to create a new object for force refresh
     *
     * Create a new datasource.
     * @return a new MovieDataSource
     */
    private MoviesDataSource getMoviesDataSource() {
        return new MoviesDataSource(apiService, apiKey);
    }

    @Override
    public DataSource<Integer, Movie> create() {
        Timber.d("Creating data source for movies");
        MoviesDataSource dataSource = getMoviesDataSource();
        sourceData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<MoviesDataSource> getSourceData() {
        return sourceData;
    }
}
