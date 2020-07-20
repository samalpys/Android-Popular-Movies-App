package com.example.android.popularmoviesapp.viewmodel;

import android.app.Application;

import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.network.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MovieViewModel extends AndroidViewModel {
    private final LiveData<Movie> movieDetailsObservable;

    public MovieViewModel(@NonNull Application application) {
        super(application);

        // change later to factory so that we can send different ids
        movieDetailsObservable = Repository.getInstance().getMovieDetails(1);
    }
}
