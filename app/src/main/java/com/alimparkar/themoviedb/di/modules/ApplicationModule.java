package com.alimparkar.themoviedb.di.modules;

import android.app.Application;
import android.content.Context;
import com.alimparkar.themoviedb.ui.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by alimparkar on 23/04/18.
 */
@Module(subcomponents = ViewModelSubComponent.class)
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    public Context provideContext() {
        return application;
    }

    @Provides
    ViewModelFactory provideViewModelFactory(ViewModelSubComponent.Builder builder) {
        return new ViewModelFactory(builder.build());
    }
}
