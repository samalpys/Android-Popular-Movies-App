package com.example.android.popularmoviesapp.viewmodel;

import android.app.Application;

import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.network.Repository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavouriteMovieViewModel extends AndroidViewModel {
    private Repository repository;

    public FavouriteMovieViewModel(@NonNull Application application) {
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
