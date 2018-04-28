package com.alimparkar.themoviedb;

import android.app.Activity;
import android.app.Application;
import com.alimparkar.themoviedb.di.ApplicationComponent;
import com.alimparkar.themoviedb.di.DaggerApplicationComponent;
import com.alimparkar.themoviedb.di.modules.ApplicationModule;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by alimparkar on 22/04/18.
 */

public class TvApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationComponent component = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .build();

        component.inject(this);

        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
