package com.example.android.popularmoviesapp.viewmodels;

import android.app.Application;

import com.example.android.popularmoviesapp.data.models.Movie;
import com.example.android.popularmoviesapp.data.Repository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavouriteMoviesViewModel extends AndroidViewModel {
    private Repository repository;

    public FavouriteMoviesViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public LiveData<List<Movie>> getAllFavouriteMovies() {
        return repository.getAllFavouriteMovies();
    }

    public void insertFavouriteMovie(Movie movie) {
        repository.insertFavouriteMovie(movie);
    }

    public void deleteFavouriteMovieById(int id) {
        repository.deleteFavouriteMovieById(id);
    }

    public void deleteAllFavouriteMovies() {
        repository.deleteAllFavouriteMovies();
    }

    public void clearCompositeDisposable() {
        repository.clearCompositeDisposable();
    }
}
