package com.alimparkar.themoviedb.ui.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import com.alimparkar.themoviedb.Constants;
import com.alimparkar.themoviedb.R;
import com.alimparkar.themoviedb.databinding.MovieDetailBinding;
import com.alimparkar.themoviedb.models.Movie;

/**
 * Created by alimparkar on 23/04/18.
 */

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Movie movie;
        if (intent != null && intent.hasExtra(Constants.EXTRA_MOVIE_DATA)) {
            movie = intent.getParcelableExtra(Constants.EXTRA_MOVIE_DATA);
            MovieDetailBinding movieDetailBinding =
                DataBindingUtil.setContentView(this, R.layout.movie_detail);
            movieDetailBinding.setMovie(movie);

            if(actionBar != null) {
                actionBar.setTitle(movie.getTitle());
            }
        } else {
            showError(getString(R.string.failed_to_load_movie));
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
