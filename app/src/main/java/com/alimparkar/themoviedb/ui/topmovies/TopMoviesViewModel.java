package com.alimparkar.themoviedb.ui.topmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import com.alimparkar.themoviedb.models.Movie;
import com.alimparkar.themoviedb.repository.MoviesDataSourceFactory;
import com.alimparkar.themoviedb.repository.MoviesDataSource;
import com.alimparkar.themoviedb.repository.NetworkState;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * ViewModel for TopMovies. This class fetches data from the MovieDataSource
 */
public class TopMoviesViewModel extends ViewModel {

    private MoviesDataSourceFactory dataSourceFactory;
    private LiveData<PagedList<Movie>> data;
    private LiveData<NetworkState> networkState;

    @Inject
    public TopMoviesViewModel(MoviesDataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
        fetchData();
    }

    public void fetchData() {
        Timber.d("Fetching data");
        PagedList.Config pageConfig = getPageListConfig();
        LivePagedListBuilder<Integer, Movie> pagedListBuilder =
            new LivePagedListBuilder<>(dataSourceFactory, pageConfig);

        data = pagedListBuilder.build();

        networkState = Transformations.switchMap(dataSourceFactory.getSourceData(),
            MoviesDataSource::getNetworkState);
    }

    private PagedList.Config getPageListConfig() {
        return new PagedList.Config.Builder().setPageSize(1)
            .setPrefetchDistance(2)
            .setEnablePlaceholders(false)
            .build();
    }

    public LiveData<PagedList<Movie>> getMovies() {
        return data;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public void retry() {
        MoviesDataSource source = dataSourceFactory.getSourceData().getValue();
        if(source != null) {
            source.invalidate();
        }
    }
}
