package com.alimparkar.themoviedb.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import com.alimparkar.themoviedb.di.modules.ViewModelSubComponent;
import com.alimparkar.themoviedb.ui.topmovies.TopMoviesViewModel;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final ArrayMap<Class, Callable<? extends ViewModel>> creators;

    @Inject
    public ViewModelFactory(ViewModelSubComponent viewModelSubComponent) {
        creators = new ArrayMap<>();

        // View models cannot be injected directly because they won't be bound to the owner's
        // view model scope.
        creators.put(TopMoviesViewModel.class, viewModelSubComponent::provideTopMoviesViewModel);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Callable<? extends ViewModel> creator = creators.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class, Callable<? extends ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("Unknown model class " + modelClass);
        }
        try {
            return (T) creator.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}