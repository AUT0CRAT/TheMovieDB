package com.alimparkar.themoviedb.ui.topmovies;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import com.alimparkar.themoviedb.Constants;
import com.alimparkar.themoviedb.R;
import com.alimparkar.themoviedb.databinding.MovieListBinding;
import com.alimparkar.themoviedb.models.Movie;
import com.alimparkar.themoviedb.repository.NetworkState;
import com.alimparkar.themoviedb.ui.ViewModelFactory;
import com.alimparkar.themoviedb.ui.detail.MovieDetailActivity;
import dagger.android.AndroidInjection;
import javax.inject.Inject;
import timber.log.Timber;

public class MovieListActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    TopMoviesAdapter adapter;

    private MovieListBinding binding;

    private TopMoviesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.movie_list);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TopMoviesViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setAdapter(adapter);

        fetchAndObserveData();
        adapter.setListener(this::clickHandler);
        binding.refreshLayout.setOnRefreshListener(viewModel::retry);
    }

    private void clickHandler(Movie movie) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(Constants.EXTRA_MOVIE_DATA, movie);
            startActivity(intent);
        }
    }

    private void fetchAndObserveData() {
        viewModel.getMovies().observe(this, this::listChanged);
        viewModel.getNetworkState().observe(this, this::networkStateChanged);
    }

    private void listChanged(PagedList<Movie> movies) {
        //TODO handle empty response
        adapter.setMovies(movies);
    }

    private void networkStateChanged(NetworkState networkState) {
        Timber.d("Network state change : " + networkState);
        switch (networkState.getStatus()) {
            case RUNNING:
                binding.refreshLayout.setRefreshing(true);
                break;
            case FAILED:
                binding.refreshLayout.setRefreshing(false);
                showError(networkState.getMessage());
                break;
            case SUCCESS:
                binding.refreshLayout.setRefreshing(false);
        }
    }

    private void showError(String message) {
        Snackbar.make(binding.refreshLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}
