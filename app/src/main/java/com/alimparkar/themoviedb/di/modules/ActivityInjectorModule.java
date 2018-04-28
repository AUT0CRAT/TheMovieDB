package com.alimparkar.themoviedb.di.modules;

import com.alimparkar.themoviedb.ui.topmovies.MovieListActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alimparkar on 23/04/18.
 */
@Module
public interface ActivityInjectorModule {

    @ContributesAndroidInjector
    MovieListActivity contributeMovieList();
}
