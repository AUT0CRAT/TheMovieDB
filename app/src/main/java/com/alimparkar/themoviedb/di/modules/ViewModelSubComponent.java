package com.alimparkar.themoviedb.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.v4.util.ArrayMap;
import com.alimparkar.themoviedb.ui.topmovies.TopMoviesViewModel;
import dagger.Subcomponent;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alimparkar on 23/04/18.
 */
@Subcomponent
public interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    TopMoviesViewModel provideTopMoviesViewModel();
}
