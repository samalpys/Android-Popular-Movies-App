package com.example.android.popularmoviesapp.viewmodel;

import android.app.Application;

import com.example.android.popularmoviesapp.model.MovieResponse;
import com.example.android.popularmoviesapp.network.Repository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

// TODO: replace by Factory
public class MovieResponseViewModel extends AndroidViewModel {
    private final LiveData<MovieResponse> popularMovieResponseObservable;
    private final LiveData<MovieResponse> topRatedMovieResponseObservable;

    public MovieResponseViewModel(Application application) {
        super(application);

        popularMovieResponseObservable = Repository.getInstance().getPopularMovies(1);
        topRatedMovieResponseObservable = Repository.getInstance().getTopRatedMovies(1);
    }

    public LiveData<MovieResponse> getPopularMovieResponseObservable() {
        return popularMovieResponseObservable;
    }

    public LiveData<MovieResponse> getTopRatedMovieResponseObservable() {
        return topRatedMovieResponseObservable;
    }
}
