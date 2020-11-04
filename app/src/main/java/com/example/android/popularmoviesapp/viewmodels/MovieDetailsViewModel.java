package com.example.android.popularmoviesapp.viewmodels;

import android.app.Application;

import com.example.android.popularmoviesapp.data.models.Movie;
import com.example.android.popularmoviesapp.data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MovieDetailsViewModel extends AndroidViewModel {
    private Repository repository;
    private long movieId;

    public MovieDetailsViewModel(@NonNull Application application, long movieId) {
        super(application);
        repository = Repository.getInstance(application);
        this.movieId = movieId;
    }

//    public LiveData<Movie> getMovieDetails() {
//        return  repository.getMovieDetails(movieId);
//    }

    public LiveData<Movie> getMovieDetailsWithRx() {
        return  repository.getMovieDetailsWithRx(movieId);
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
            return (T) new MovieDetailsViewModel(application, id);
        }
    }


}
