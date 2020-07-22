package com.example.android.popularmoviesapp.viewmodel;

import android.app.Application;

import com.example.android.popularmoviesapp.model.MovieResponse;
import com.example.android.popularmoviesapp.network.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class MovieResponseViewModel extends AndroidViewModel {
    private final LiveData<MovieResponse> movieResponseObservable;

    public MovieResponseViewModel(Application application, String sortBy) {
        super(application);

        movieResponseObservable = Repository.getInstance().getMovies(sortBy, 1);
    }

    public LiveData<MovieResponse> getMovieResponseObservable() {
        return movieResponseObservable;
    }

    public static class MovieResponseViewModelFactory implements ViewModelProvider.Factory {
        private Application application;
        private String sortBy;

        public MovieResponseViewModelFactory(Application application, String sortBy) {
            this.application = application;
            this.sortBy = sortBy;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MovieResponseViewModel(application, sortBy);
        }
    }
}
