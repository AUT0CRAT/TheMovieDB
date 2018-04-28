package com.alimparkar.themoviedb.di;

import com.alimparkar.themoviedb.TvApplication;
import com.alimparkar.themoviedb.di.modules.ActivityInjectorModule;
import com.alimparkar.themoviedb.di.modules.ApplicationModule;
import com.alimparkar.themoviedb.di.modules.NetworkModule;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import javax.inject.Singleton;

/**
 * Created by alimparkar on 22/04/18.
 */
@Component(modules = {
    AndroidInjectionModule.class, ApplicationModule.class, NetworkModule.class,
    ActivityInjectorModule.class
})
@Singleton
public interface ApplicationComponent {
    void inject(TvApplication application);
}
