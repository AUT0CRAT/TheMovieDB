package com.alimparkar.themoviedb.ui.topmovies;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.View;
import com.alimparkar.themoviedb.R;
import com.alimparkar.themoviedb.models.Movie;
import com.alimparkar.themoviedb.ui.base.BindablePagedAdapter;
import com.alimparkar.themoviedb.ui.base.BindingViewHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by alimparkar on 23/04/18.
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


    public void setMovies(PagedList<Movie> movies) {
        submitList(movies);
        notifyDataSetChanged();
    }
}
