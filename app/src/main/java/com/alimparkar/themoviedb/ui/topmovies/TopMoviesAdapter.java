package com.alimparkar.themoviedb.ui.topmovies;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import com.alimparkar.themoviedb.R;
import com.alimparkar.themoviedb.models.Movie;
import com.alimparkar.themoviedb.ui.base.BindablePagedAdapter;
import javax.inject.Inject;

/**
 * Adapter that will bind data to the recycler view using Databinding
 */
public class TopMoviesAdapter extends BindablePagedAdapter<Movie> {

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<Movie>() {
            @Override
            public boolean areItemsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
                return oldMovie.getId() == newMovie.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
                return oldMovie.equals(newMovie);
            }
        };

    @Inject
    public TopMoviesAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_movie;
    }

    void setMovies(PagedList<Movie> movies) {
        submitList(movies);
        notifyDataSetChanged();
    }
}
