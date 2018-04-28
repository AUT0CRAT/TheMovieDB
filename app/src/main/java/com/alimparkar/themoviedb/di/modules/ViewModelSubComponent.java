package com.alimparkar.themoviedb.di.modules;

import com.alimparkar.themoviedb.ui.topmovies.TopMoviesViewModel;
import dagger.Subcomponent;

/**
 * Created by alimparkar on 23/04/18.
 */
@Subcomponent
public interface ViewModelSubComponent {

    TopMoviesViewModel provideTopMoviesViewModel();

    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }
}
