package com.example.android.popularmoviesapp.viewmodel;

import android.app.Application;

import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.network.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MovieViewModel extends AndroidViewModel {
    private final LiveData<Movie> movieDetailsObservable;

    public MovieViewModel(@NonNull Application application, long movieId) {
        super(application);
        movieDetailsObservable = Repository.getInstance().getMovieDetails(movieId);
    }

    public LiveData<Movie> getMovieDetailsObservable() {
        return  movieDetailsObservable;
    }

    public static class MovieViewModelFactory implements ViewModelProvider.Factory {
        private Application application;
        private long id;

        public MovieViewModelFactory(Application application, long id) {
            this.application = application;
            this.id = id;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MovieViewModel(application, id);
        }
    }


}
